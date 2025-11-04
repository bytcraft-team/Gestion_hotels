package APiRes.Models

import jakarta.persistence.*
import java.time.LocalDate

/**
 * Représente une réservation en ligne dans le système de gestion des hôtels.
 *
 * Cette classe hérite de [Reservation] et ajoute des fonctionnalités spécifiques
 * aux réservations effectuées via une plateforme en ligne, comme le nom de la
 * plateforme et la remise appliquée.
 *
 * @property plateforme Nom de la plateforme utilisée pour la réservation (ex : "SiteWeb", "MobileApp").
 * @property remise Pourcentage de remise appliqué sur le montant total de la réservation (0.0 par défaut).
 *
 * @constructor Crée une instance de [ReservationOnline] avec les informations de base
 * et les paramètres spécifiques aux réservations en ligne.
 *
 * @param dateDebut Date de début de la réservation.
 * @param dateFin Date de fin de la réservation.
 * @param client Client effectuant la réservation.
 * @param chambre Chambre réservée.
 * @param employe Employé associé à la réservation (optionnel).
 * @param plateforme Plateforme de réservation (par défaut "SiteWeb").
 * @param remise Remise appliquée sur le montant total (valeur entre 0.0 et 1.0, par défaut 0.0).
 *
 * @see Reservation pour les propriétés et méthodes communes aux réservations.
 */
@Entity
@Table(name = "reservations_online")
class ReservationOnline(
    dateDebut: LocalDate,
    dateFin: LocalDate,
    client: Client,
    chambre: Chambre,
    employe: Employee? = null,
    /** Plateforme utilisée pour la réservation (ex : SiteWeb, MobileApp). */
    var plateforme: String = "SiteWeb",
    /** Remise appliquée sur le montant total (0.0 par défaut). */
    var remise: Double = 0.0
) : Reservation(
    dateDebut = dateDebut,
    dateFin = dateFin,
    client = client,
    chambre = chambre,
    employe = employe
) {

    /**
     * Confirme la réservation en ligne.
     *
     * @param by Employé qui confirme la réservation (optionnel).
     * Appelle la méthode parente [Reservation.confirmer] puis affiche un message spécifique.
     */
    override fun confirmer(by: Employee?) {
        super.confirmer(by)
        println("🌐 Réservation en ligne confirmée via $plateforme")
    }

    /**
     * Calcule le montant total de la réservation en tenant compte de la remise en ligne.
     *
     * @return Montant total après application de la remise.
     */
    override fun calculerMontant(): Double {
        val total = super.calculerMontant()
        return total * (1 - remise)
    }

    /**
     * Retourne une description complète de la réservation en ligne.
     *
     * @return Chaîne contenant l’ID, le statut, le client, la chambre, la plateforme,
     * le pourcentage de remise et le montant total après remise.
     */
    override fun afficher(): String =
        "Réservation Online $reservationId [$statut] via $plateforme : " +
                "${client.prenom} ${client.nom} -> Chambre ${chambre.numero} " +
                "(Remise: ${(remise * 100).toInt()}%) - ${calculerMontant()} DH"
}
