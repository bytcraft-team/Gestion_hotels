package APiRes.Models

import jakarta.persistence.*
import java.time.LocalDate
import com.fasterxml.jackson.annotation.JsonManagedReference

/**
 * Représente un client dans le système de gestion des hôtels.
 *
 * Cette classe est mappée sur la table **"clients"** et peut avoir plusieurs
 * réservations associées.
 *
 * @property clientId Identifiant unique du client (généré automatiquement par la base de données).
 * @property nom Nom du client.
 * @property prenom Prénom du client.
 * @property email Adresse email du client.
 * @property telephone Numéro de téléphone du client.
 * @property reservations Liste des réservations effectuées par ce client.
 *
 * @constructor Crée une instance de [Client] avec les informations de base.
 *
 * @see Reservation pour la relation entre le client et ses réservations.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "clients")
open class Client(

    /** Identifiant unique du client (clé primaire auto-générée). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    var clientId: Int? = null,

    /** Nom du client. */
    var nom: String = "",

    /** Prénom du client. */
    var prenom: String = "",

    /** Email du client. */
    var email: String = "",

    /** Numéro de téléphone du client. */
    var telephone: String = ""

) {

    /**
     * Liste des réservations associées à ce client.
     * Une relation One-to-Many vers [Reservation].
     */
    @OneToMany(mappedBy = "client", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @field:JsonManagedReference
    var reservations: MutableList<Reservation> = mutableListOf()

    /**
     * Crée une réservation pour ce client.
     *
     * @param chambre Chambre à réserver.
     * @param dateDebut Date de début de la réservation.
     * @param dateFin Date de fin de la réservation.
     * @return La réservation créée.
     */
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

    /**
     * Retourne une description textuelle du client.
     *
     * @return Chaîne contenant le prénom, le nom, l’ID, l’email et le téléphone.
     */
    open fun afficher(): String =
        "$prenom $nom (id=$clientId) - $email - $telephone"
}
