package APiRes.Models

import jakarta.persistence.*
import java.time.LocalDate

/**
 * Représente un client VIP dans le système de gestion des hôtels.
 *
 * Cette classe hérite de [Client] et ajoute des fonctionnalités spécifiques
 * aux clients VIP, comme une remise sur les réservations et la réservation en ligne.
 *
 * Les clients VIP utilisent la classe [ReservationOnline] pour leurs réservations.
 *
 * @property remise Pourcentage de remise appliqué aux réservations (par défaut 15% = 0.15).
 *
 * @constructor Crée une instance de [ClientVIP] avec les informations de base
 * et le taux de remise.
 *
 * @see Client pour les propriétés et comportements communs aux clients.
 * @see ReservationOnline pour la réservation spécifique aux clients VIP.
 */
@Entity
@Table(name = "clients_vip")
class ClientVIP(

    /** Nom du client VIP. */
    nom: String = "",

    /** Prénom du client VIP. */
    prenom: String = "",

    /** Email du client VIP. */
    email: String = "",

    /** Numéro de téléphone du client VIP. */
    telephone: String = "",

    /** Remise appliquée aux réservations du client VIP (valeur par défaut 0.15 = 15%). */
    var remise: Double = 0.15

) : Client(
    nom = nom,
    prenom = prenom,
    email = email,
    telephone = telephone
) {

    /**
     * Crée une réservation en ligne pour ce client VIP avec remise.
     *
     * @param chambre Chambre à réserver.
     * @param dateDebut Date de début de la réservation.
     * @param dateFin Date de fin de la réservation.
     * @return La réservation en ligne créée ([ReservationOnline]).
     */
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

    /**
     * Retourne une description complète du client VIP.
     *
     * @return Chaîne contenant le prénom, le nom, l’ID, l’email, le téléphone
     * et le statut VIP avec le pourcentage de remise.
     */
    override fun afficher(): String =
        "${super.afficher()} - VIP (${(remise * 100).toInt()}%)"
}
