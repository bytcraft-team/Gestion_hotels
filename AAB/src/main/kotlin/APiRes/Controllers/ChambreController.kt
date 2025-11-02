package APiRes.Controllers

import APiRes.DTO.ChambreDTO
import APiRes.DTO.ChambreSuiteDTO
import APiRes.Models.Chambre
import APiRes.Models.ChambreSuite
import APiRes.Service.ChambreService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chambres")
class ChambreController(private val chambreService: ChambreService) {

    @GetMapping
    fun getAllChambres(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "chambreId") sortBy: String
    ): ResponseEntity<Page<Chambre>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortBy))
        return ResponseEntity.ok(chambreService.getAllChambres(pageable))
    }

    @GetMapping("/{id}")
    fun getChambreById(@PathVariable id: Int): ResponseEntity<Chambre> {
        return ResponseEntity.ok(chambreService.getChambreById(id))
    }

    @PostMapping
    fun addChambre(@Valid @RequestBody dto: ChambreDTO): ResponseEntity<Chambre> {
        val chambre = Chambre(
            numero = dto.numero,
            prix = dto.prix,
            typeChambre = dto.typeChambre
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(chambreService.addChambre(chambre))
    }

    @PostMapping("/suite")
    fun addSuite(@Valid @RequestBody dto: ChambreSuiteDTO): ResponseEntity<Chambre> {
        val suite = ChambreSuite(
            numero = dto.numero,
            prix = dto.prix,
            suiteNom = dto.suiteNom,
            nombrePieces = dto.nombrePieces,
            jacuzzi = dto.jacuzzi
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(chambreService.addSuite(suite))
    }

    @PutMapping("/{id}")
    fun updateChambre(
        @PathVariable id: Int,
        @Valid @RequestBody dto: ChambreDTO
    ): ResponseEntity<Chambre> {
        val updated = Chambre(
            numero = dto.numero,
            prix = dto.prix,
            typeChambre = dto.typeChambre
        )
        return ResponseEntity.ok(chambreService.updateChambre(id, updated))
    }

    @DeleteMapping("/{id}")
    fun deleteChambre(@PathVariable id: Int): ResponseEntity<Void> {
        chambreService.deleteChambre(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/type/{type}")
    fun findByType(@PathVariable type: String): ResponseEntity<List<Chambre>> {
        return ResponseEntity.ok(chambreService.findByType(type))
    }

    @GetMapping("/prix-max/{maxPrix}")
    fun findByMaxPrix(@PathVariable maxPrix: Double): ResponseEntity<List<Chambre>> {
        return ResponseEntity.ok(chambreService.findByMaxPrix(maxPrix))
    }
}