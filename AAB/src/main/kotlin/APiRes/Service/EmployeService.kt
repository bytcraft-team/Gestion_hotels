package APiRes.Service

import APiRes.Exception.ResourceNotFoundException
import APiRes.Exception.BadRequestException
import APiRes.Models.Employee
import APiRes.Repository.EmployeeRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class EmployeeService(private val employeeRepo: EmployeeRepository) {

    fun getAllEmployees(pageable: Pageable): Page<Employee> =
        employeeRepo.findAll(pageable)

    fun getEmployeeById(id: Int): Employee =
        employeeRepo.findById(id)
            .orElseThrow { ResourceNotFoundException("Employé avec l'ID $id introuvable") }

    fun addEmployee(employee: Employee): Employee {
        if (employee.nom.isBlank()) throw BadRequestException("Le nom ne peut pas être vide")
        if (employee.salaire < 0) throw BadRequestException("Le salaire ne peut pas être négatif")
        return employeeRepo.save(employee)
    }

    fun updateEmployee(id: Int, updated: Employee): Employee {
        val existing = employeeRepo.findById(id)
            .orElseThrow { ResourceNotFoundException("Employé avec l'ID $id introuvable") }

        if (updated.nom.isBlank()) throw BadRequestException("Le nom ne peut pas être vide")
        if (updated.salaire < 0) throw BadRequestException("Le salaire ne peut pas être négatif")

        existing.nom = updated.nom
        existing.poste = updated.poste
        existing.salaire = updated.salaire
        return employeeRepo.save(existing)
    }

    fun deleteEmployee(id: Int) {
        if (!employeeRepo.existsById(id)) {
            throw ResourceNotFoundException("Employé avec l'ID $id introuvable")
        }
        employeeRepo.deleteById(id)
    }

    fun findByNom(nom: String): List<Employee> = employeeRepo.findByNom(nom)

    fun findByPoste(poste: String): List<Employee> = employeeRepo.findByPoste(poste)
}