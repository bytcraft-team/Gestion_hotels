package APiRes.Repository
import APiRes.Models.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Int> {
    fun findByNom(nom: String): List<Employee>
    fun findByPoste(poste: String): List<Employee>
}