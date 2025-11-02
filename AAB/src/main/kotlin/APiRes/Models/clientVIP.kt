package APiRes.Models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "clients_vip")
class ClientVIP(
    nom: String = "",
    prenom: String = "",
    email: String = "",
    telephone: String = "",
    var remise: Double = 0.15
) : Client(
    nom = nom,
    prenom = prenom,
    email = email,
    telephone = telephone
) {

    override fun reserver(chambre: Chambre, dateDebut: LocalDate, dateFin: LocalDate): ReservationOnline {
        val reservation = ReservationOnline(
            dateDebut = dateDebut,
            dateFin = dateFin,
            client = this,
            chambre = chambre,
            employe = null,
            plateforme = "SiteWeb",
            remise = remise
        )
        reservations.add(reservation)
        return reservation
    }

    override fun afficher(): String =
        "${super.afficher()} - VIP (${(remise * 100).toInt()}%)"
}