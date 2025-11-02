package APiRes.Controllers

import APiRes.DTO.EmployeeDTO
import APiRes.Models.Employee
import APiRes.Service.EmployeeService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/employees")
class EmployeeController(private val employeeService: EmployeeService) {

    @GetMapping
    fun getAllEmployees(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "employeId") sortBy: String
    ): ResponseEntity<Page<Employee>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortBy))
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable))
    }

    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable id: Int): ResponseEntity<Employee> {
        return ResponseEntity.ok(employeeService.getEmployeeById(id))
    }

    @PostMapping
    fun addEmployee(@Valid @RequestBody dto: EmployeeDTO): ResponseEntity<Employee> {
        val employee = Employee(
            nom = dto.nom,
            poste = dto.poste,
            salaire = dto.salaire
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.addEmployee(employee))
    }

    @PutMapping("/{id}")
    fun updateEmployee(
        @PathVariable id: Int,
        @Valid @RequestBody dto: EmployeeDTO
    ): ResponseEntity<Employee> {
        val updated = Employee(
            nom = dto.nom,
            poste = dto.poste,
            salaire = dto.salaire
        )
        return ResponseEntity.ok(employeeService.updateEmployee(id, updated))
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: Int): ResponseEntity<Void> {
        employeeService.deleteEmployee(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search/nom/{nom}")
    fun findByNom(@PathVariable nom: String): ResponseEntity<List<Employee>> {
        return ResponseEntity.ok(employeeService.findByNom(nom))
    }

    @GetMapping("/search/poste/{poste}")
    fun findByPoste(@PathVariable poste: String): ResponseEntity<List<Employee>> {
        return ResponseEntity.ok(employeeService.findByPoste(poste))
    }
}