package APiRes.Repository

import APiRes.Models.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Int> {
    fun findByNom(nom: String): List<Client>
}
