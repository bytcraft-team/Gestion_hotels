package APiRes.Repository
import APiRes.Models.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ReservationRepository : JpaRepository<Reservation, Int> {
    fun findByClient_ClientId(clientId: Int): List<Reservation>
    fun findByChambre_ChambreId(chambreId: Int): List<Reservation>
    fun findByDateDebutBetween(start: LocalDate, end: LocalDate): List<Reservation>
    fun findByStatut(statut: String): List<Reservation>
}
