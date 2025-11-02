package APiRes.Models

import jakarta.persistence.*

@Entity
@DiscriminatorValue("SUITE")
class ChambreSuite(
    numero: Int,
    prix: Double,

    @Column(name = "suite_nom")
    var suiteNom: String,

    @Column(name = "nombre_pieces")
    var nombrePieces: Int = 2,

    var jacuzzi: Boolean = false
) : Chambre(numero = numero, prix = prix) {

    override var typeChambre: String = "SUITE"

    override fun afficher(): String =
        "Suite $numero (id=$chambreId) - $suiteNom - $nombrePieces pièces - " +
                "Jacuzzi: ${if(jacuzzi) "Oui" else "Non"} - $prix DH"
}