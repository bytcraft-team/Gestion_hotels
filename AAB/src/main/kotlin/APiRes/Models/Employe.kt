package APiRes.Models

import jakarta.persistence.*

@Entity
@Table(name = "employees")
open class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employe_id")
    var employeId: Int? = null,

    var nom: String = "",
    var poste: String = "",
    var salaire: Double = 0.0
) {
    @OneToMany(mappedBy = "employe", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var reservations: MutableList<Reservation> = mutableListOf()

    open fun travailler(): String = "$nom (id=$employeId) travaille au poste de $poste."

    fun augmenterSalaire(montant: Double) {
        if (montant > 0) {
            salaire += montant
            println("✅ Salaire de $nom augmenté de $montant DH. Nouveau salaire : $salaire DH")
        } else {
            println("❌ Le montant doit être positif.")
        }
    }

    fun changerPoste(nouveauPoste: String) {
        if (nouveauPoste.isNotBlank()) {
            val ancienPoste = poste
            poste = nouveauPoste
            println("✅ $nom a changé de poste : $ancienPoste → $nouveauPoste")
        } else {
            println("❌ Le nouveau poste ne peut pas être vide.")
        }
    }
}