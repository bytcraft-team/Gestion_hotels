package APiRes.Models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "clients")
open class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    var clientId: Int? = null,

    var nom: String = "",
    var prenom: String = "",
    var email: String = "",
    var telephone: String = ""
) {

    @OneToMany(mappedBy = "client", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var reservations: MutableList<Reservation> = mutableListOf()

    open fun reserver(chambre: Chambre, dateDebut: LocalDate, dateFin: LocalDate): Reservation {
        val reservation = Reservation(
            dateDebut = dateDebut,
            dateFin = dateFin,
            client = this,
            chambre = chambre,
            employe = null
        )
        reservations.add(reservation)
        return reservation
    }

    open fun afficher(): String =
        "$prenom $nom (id=$clientId) - $email - $telephone"
}