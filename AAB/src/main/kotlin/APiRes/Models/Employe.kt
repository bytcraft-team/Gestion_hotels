package APiRes.Models

import jakarta.persistence.*

/**
 * Représente un employé dans le système de gestion des hôtels.
 *
 * Cette classe est mappée sur la table **"employees"** et peut être associée
 * à plusieurs réservations.
 *
 * @property employeId Identifiant unique de l’employé (généré automatiquement par la base de données).
 * @property nom Nom de l’employé.
 * @property poste Poste occupé par l’employé.
 * @property salaire Salaire actuel de l’employé en dirhams.
 * @property reservations Liste des réservations gérées par cet employé.
 *
 * @constructor Crée une instance de [Employee] avec les informations de base.
 *
 * @see Reservation pour la relation entre l’employé et les réservations.
 */
@Entity
@Table(name = "employees")
open class Employee(

    /** Identifiant unique de l’employé (clé primaire auto-générée). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employe_id")
    var employeId: Int? = null,

    /** Nom de l’employé. */
    var nom: String = "",

    /** Poste occupé par l’employé. */
    var poste: String = "",

    /** Salaire actuel de l’employé en dirhams (DH). */
    var salaire: Double = 0.0

) {

    /**
     * Liste des réservations associées à cet employé.
     * Une relation One-to-Many vers [Reservation].
     */
    @OneToMany(mappedBy = "employe", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var reservations: MutableList<Reservation> = mutableListOf()

    /**
     * Retourne une description du travail de l’employé.
     *
     * @return Chaîne indiquant le nom, l’ID et le poste de l’employé.
     */
    open fun travailler(): String = "$nom (id=$employeId) travaille au poste de $poste."

    /**
     * Augmente le salaire de l’employé.
     *
     * @param montant Montant à ajouter au salaire (doit être positif).
     */
    fun augmenterSalaire(montant: Double) {
        if (montant > 0) {
            salaire += montant
            println("Salaire de $nom augmenté de $montant DH. Nouveau salaire : $salaire DH")
        } else {
            println(" Le montant doit être positif.")
        }
    }

    /**
     * Change le poste de l’employé.
     *
     * @param nouveauPoste Nouveau poste à attribuer (ne peut pas être vide).
     */
    fun changerPoste(nouveauPoste: String) {
        if (nouveauPoste.isNotBlank()) {
            val ancienPoste = poste
            poste = nouveauPoste
            println(" $nom a changé de poste : $ancienPoste → $nouveauPoste")
        } else {
            println(" Le nouveau poste ne peut pas être vide.")
        }
    }
}
