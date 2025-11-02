package APiRes.Models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "reservations_online")
class ReservationOnline(
    dateDebut: LocalDate,
    dateFin: LocalDate,
    client: Client,
    chambre: Chambre,
    employe: Employee? = null,
    var plateforme: String = "SiteWeb",
    var remise: Double = 0.0
) : Reservation(
    dateDebut = dateDebut,
    dateFin = dateFin,
    client = client,
    chambre = chambre,
    employe = employe
) {

    override fun confirmer(by: Employee?) {
        super.confirmer(by)
        println("🌐 Réservation en ligne confirmée via $plateforme")
    }

    override fun calculerMontant(): Double {
        val total = super.calculerMontant()
        return total * (1 - remise)
    }

    override fun afficher(): String =
        "Réservation Online $reservationId [$statut] via $plateforme : " +
                "${client.prenom} ${client.nom} -> Chambre ${chambre.numero} " +
                "(Remise: ${(remise * 100).toInt()}%) - ${calculerMontant()} DH"
}