package APiRes.Models

import jakarta.persistence.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import com.fasterxml.jackson.annotation.JsonBackReference

/**
 * Représente une réservation de chambre dans le système de gestion des hôtels.
 *
 * Cette classe est mappée sur la table **"reservations"**.
 * Elle peut être étendue pour des types de réservation spécifiques (ex : [ReservationOnline]).
 *
 * @property reservationId Identifiant unique de la réservation (généré automatiquement par la base de données).
 * @property dateDebut Date de début de la réservation.
 * @property dateFin Date de fin de la réservation.
 * @property statut Statut actuel de la réservation (ex : "EN_ATTENTE", "CONFIRMEE", "ANNULEE").
 * @property client Client qui effectue la réservation.
 * @property chambre Chambre réservée.
 * @property employe Employé ayant confirmé ou géré la réservation (optionnel).
 *
 * @constructor Crée une instance de [Reservation] avec les informations de base.
 *
 * @see Client pour le client associé à la réservation.
 * @see Chambre pour la chambre réservée.
 * @see Employee pour l’employé gérant la réservation.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "reservations")
open class Reservation(

    /** Identifiant unique de la réservation (clé primaire auto-générée). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    var reservationId: Int? = null,

    /** Date de début de la réservation. */
    @Column(name = "date_debut")
    var dateDebut: LocalDate,

    /** Date de fin de la réservation. */
    @Column(name = "date_fin")
    var dateFin: LocalDate,

    /** Statut de la réservation. Valeurs possibles : "EN_ATTENTE", "CONFIRMEE", "ANNULEE". */
    var statut: String = "EN_ATTENTE",

    /** Client qui effectue la réservation. */
    @ManyToOne
    @JoinColumn(name = "client_id")
    @field:JsonBackReference
    var client: Client,

    /** Chambre réservée. */
    @ManyToOne
    @JoinColumn(name = "chambre_id")
    var chambre: Chambre,

    /** Employé qui confirme ou gère la réservation (optionnel). */
    @ManyToOne
    @JoinColumn(name = "employe_id")
    var employe: Employee? = null

) {

    /**
     * Confirme la réservation.
     *
     * @param by Employé qui confirme la réservation (optionnel).
     * Si `null`, la confirmation est automatique.
     */
    open fun confirmer(by: Employee?) {
        if (by != null) {
            employe = by
            statut = "CONFIRMEE"
            println("Réservation $reservationId confirmée par ${by.nom}")
        } else {
            statut = "CONFIRMEE"
            println(" Réservation $reservationId confirmée automatiquement")
        }
    }

    /**
     * Annule la réservation.
     *
     * @param by Employé qui annule la réservation (optionnel).
     * Si `null`, l’annulation est faite par le système.
     */
    open fun annuler(by: Employee?) {
        employe = by
        statut = "ANNULEE"
        println(" Réservation $reservationId annulée par ${by?.nom ?: "système"}")
    }

    /**
     * Calcule le montant total de la réservation.
     *
     * Le calcul se base sur le prix de la chambre et le nombre de jours de réservation.
     * Minimum 1 jour.
     *
     * @return Montant total en dirhams (DH).
     */
    open fun calculerMontant(): Double {
        val jours = ChronoUnit.DAYS.between(dateDebut, dateFin).coerceAtLeast(1)
        return chambre.prix * jours
    }

    /**
     * Retourne une description complète de la réservation.
     *
     * @return Chaîne contenant l’ID, le statut, le client, la chambre, les dates et le montant.
     */
    open fun afficher(): String =
        "Réservation $reservationId [$statut] : ${client.prenom} ${client.nom} -> " +
                "Chambre ${chambre.numero} du $dateDebut au $dateFin - ${calculerMontant()} DH"
}
