package APiRes.Service

import APiRes.Exception.ResourceNotFoundException
import APiRes.Exception.BadRequestException
import APiRes.Models.Chambre
import APiRes.Models.ChambreSuite
import APiRes.Repository.ChambreRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ChambreService(private val chambreRepo: ChambreRepository) {

    fun getAllChambres(pageable: Pageable): Page<Chambre> =
        chambreRepo.findAll(pageable)

    fun getChambreById(id: Int): Chambre =
        chambreRepo.findById(id)
            .orElseThrow { ResourceNotFoundException("Chambre avec l'ID $id introuvable") }

    fun addChambre(chambre: Chambre): Chambre {
        if (chambre.prix < 0) throw BadRequestException("Le prix ne peut pas être négatif")
        if (chambre.numero <= 0) throw BadRequestException("Le numéro de chambre doit être positif")
        return chambreRepo.save(chambre)
    }

    fun addSuite(suite: ChambreSuite): Chambre {
        if (suite.prix < 0) throw BadRequestException("Le prix ne peut pas être négatif")
        if (suite.nombrePieces <= 0) throw BadRequestException("Le nombre de pièces doit être positif")
        return chambreRepo.save(suite)
    }

    fun updateChambre(id: Int, updated: Chambre): Chambre {
        val existing = chambreRepo.findById(id)
            .orElseThrow { ResourceNotFoundException("Chambre avec l'ID $id introuvable") }

        if (updated.prix < 0) throw BadRequestException("Le prix ne peut pas être négatif")
        if (updated.numero <= 0) throw BadRequestException("Le numéro de chambre doit être positif")

        existing.numero = updated.numero
        existing.typeChambre = updated.typeChambre
        existing.prix = updated.prix
        return chambreRepo.save(existing)
    }

    fun deleteChambre(id: Int) {
        if (!chambreRepo.existsById(id)) {
            throw ResourceNotFoundException("Chambre avec l'ID $id introuvable")
        }
        chambreRepo.deleteById(id)
    }

    fun findByType(type: String): List<Chambre> = chambreRepo.findByTypeChambre(type)

    fun findByMaxPrix(maxPrix: Double): List<Chambre> = chambreRepo.findByPrixLessThanEqual(maxPrix)
}