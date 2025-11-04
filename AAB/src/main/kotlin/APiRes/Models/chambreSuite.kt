package APiRes.Models

import jakarta.persistence.*

/**
 * Représente une chambre suite dans le système de gestion des hôtels.
 *
 * Cette classe hérite de [Chambre] et ajoute des attributs spécifiques aux suites,
 * comme le nom de la suite, le nombre de pièces et la présence d’un jacuzzi.
 *
 * Elle utilise la stratégie d’héritage `JOINED` définie dans la classe parente,
 * et la valeur de discrimination `SUITE` permet à JPA de distinguer ce type
 * de chambre dans la hiérarchie.
 *
 * @property suiteNom Nom de la suite (ex : "Royale", "Présidentielle", etc.).
 * @property nombrePieces Nombre de pièces composant la suite (par défaut 2).
 * @property jacuzzi Indique si la suite dispose d’un jacuzzi (`true` = Oui, `false` = Non).
 *
 * @constructor Crée une instance de [ChambreSuite] avec les informations de base :
 * numéro, prix, nom, nombre de pièces et présence de jacuzzi.
 *
 * @see Chambre pour les propriétés et comportements communs aux chambres.
 */
@Entity
@DiscriminatorValue("SUITE")
class ChambreSuite(

    /** Numéro unique attribué à la suite. */
    numero: Int,

    /** Prix de la suite par nuit (en dirhams). */
    prix: Double,

    /** Nom de la suite (ex : "Royale", "Luxe", "Panoramique", etc.). */
    @Column(name = "suite_nom")
    var suiteNom: String,

    /** Nombre de pièces dans la suite. Valeur par défaut : 2. */
    @Column(name = "nombre_pieces")
    var nombrePieces: Int = 2,

    /** Indique la présence d’un jacuzzi dans la suite. */
    var jacuzzi: Boolean = false

) : Chambre(numero = numero, prix = prix) {

    /** Type de chambre défini à "SUITE". */
    override var typeChambre: String = "SUITE"

    /**
     * Retourne une description complète de la suite.
     *
     * @return Chaîne contenant le numéro, le nom, le nombre de pièces,
     * la présence du jacuzzi et le prix.
     */
    override fun afficher(): String =
        "Suite $numero (id=$chambreId) - $suiteNom - $nombrePieces pièces - " +
                "Jacuzzi: ${if (jacuzzi) "Oui" else "Non"} - $prix DH"
}
