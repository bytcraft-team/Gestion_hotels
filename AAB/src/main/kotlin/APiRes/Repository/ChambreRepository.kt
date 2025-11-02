package APiRes.Repository

import APiRes.Models.Chambre
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChambreRepository : JpaRepository<Chambre, Int> {
    fun findByTypeChambre(typeChambre: String): List<Chambre>
    fun findByPrixLessThanEqual(maxPrix: Double): List<Chambre>
}
