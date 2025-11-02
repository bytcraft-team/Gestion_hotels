package APiRes.Models

import jakarta.persistence.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "reservations")
open class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    var reservationId: Int? = null,

    @Column(name = "date_debut")
    var dateDebut: LocalDate,

    @Column(name = "date_fin")
    var dateFin: LocalDate,

    var statut: String = "EN_ATTENTE",

    @ManyToOne
    @JoinColumn(name = "client_id")
    var client: Client,

    @ManyToOne
    @JoinColumn(name = "chambre_id")
    var chambre: Chambre,

    @ManyToOne
    @JoinColumn(name = "employe_id")
    var employe: Employee? = null
) {

    open fun confirmer(by: Employee?) {
        if (by != null) {
            employe = by
            statut = "CONFIRMEE"
            println("✅ Réservation $reservationId confirmée par ${by.nom}")
        } else {
            statut = "CONFIRMEE"
            println("✅ Réservation $reservationId confirmée automatiquement")
        }
    }

    open fun annuler(by: Employee?) {
        employe = by
        statut = "ANNULEE"
        println("❌ Réservation $reservationId annulée par ${by?.nom ?: "système"}")
    }

    open fun calculerMontant(): Double {
        val jours = ChronoUnit.DAYS.between(dateDebut, dateFin).coerceAtLeast(1)
        return chambre.prix * jours
    }

    open fun afficher(): String =
        "Réservation $reservationId [$statut] : ${client.prenom} ${client.nom} -> " +
                "Chambre ${chambre.numero} du $dateDebut au $dateFin - ${calculerMontant()} DH"
}