package APiRes.Models

import jakarta.persistence.*

/**
 * Représente une chambre dans le système de gestion des hôtels.
 *
 * Cette classe est une entité JPA mappée sur la table **"chambres"**.
 * Elle sert de classe de base pour les différents types de chambres (ex : Suite, Double, etc.)
 * grâce à la stratégie d’héritage `JOINED`.
 *
 * @property chambreId Identifiant unique de la chambre (généré automatiquement par la base de données).
 * @property numero Numéro unique attribué à la chambre dans l’hôtel.
 * @property prix Prix de la chambre (par nuit) en dirhams.
 * @property typeChambre Type de la chambre (par défaut "SIMPLE", mais peut être redéfini dans les sous-classes).
 * @property reservations Liste des réservations associées à cette chambre.
 *
 * @constructor Crée une instance de [Chambre] avec les valeurs spécifiées.
 *
 * @see Reservation pour la relation entre les chambres et les réservations.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "chambres")
open class Chambre(

    /** Identifiant unique de la chambre (clé primaire auto-générée). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chambre_id")
    var chambreId: Int? = null,

    /** Numéro de la chambre. */
    var numero: Int = 0,

    /** Prix de la chambre en dirhams (DH). */
    var prix: Double = 0.0,

    /** Type de la chambre (ex: SIMPLE, DOUBLE, SUITE, etc.). */
    @Column(name = "type_chambre")
    open var typeChambre: String = "SIMPLE"

) {
    /**
     * Liste des réservations associées à cette chambre.
     * Une chambre peut avoir plusieurs réservations.
     */
    @OneToMany(mappedBy = "chambre", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var reservations: MutableList<Reservation> = mutableListOf()

    /**
     * Retourne une description textuelle de la chambre.
     *
     * @return Chaîne contenant le numéro, l’ID, le type et le prix de la chambre.
     */
    open fun afficher(): String =
        "Chambre $numero (id=$chambreId) - $typeChambre - $prix DH"
}
