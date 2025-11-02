package APiRes.Models

import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "chambres")
open class Chambre(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chambre_id")
    var chambreId: Int? = null,

    var numero: Int = 0,
    var prix: Double = 0.0,

    @Column(name = "type_chambre")
    open var typeChambre: String = "SIMPLE"
) {
    @OneToMany(mappedBy = "chambre", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var reservations: MutableList<Reservation> = mutableListOf()

    open fun afficher(): String =
        "Chambre $numero (id=$chambreId) - $typeChambre - $prix DH"
}