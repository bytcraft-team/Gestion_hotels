package APiRes.Controllers

import APiRes.DTO.ClientDTO
import APiRes.DTO.ClientVIPDTO
import APiRes.Models.Client
import APiRes.Models.ClientVIP
import APiRes.Service.ClientService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clients")
class ClientController(private val clientService: ClientService) {

    @GetMapping
    fun getAllClients(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "clientId") sortBy: String
    ): ResponseEntity<Page<Client>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortBy))
        return ResponseEntity.ok(clientService.getAllClients(pageable))
    }

    @GetMapping("/{id}")
    fun getClientById(@PathVariable id: Int): ResponseEntity<Client> {
        return ResponseEntity.ok(clientService.getClientById(id))
    }

    @PostMapping
    fun createClient(@Valid @RequestBody dto: ClientDTO): ResponseEntity<Client> {
        val client = Client(
            nom = dto.nom,
            prenom = dto.prenom,
            email = dto.email,
            telephone = dto.telephone
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClient(client))
    }

    @PostMapping("/vip")
    fun createClientVIP(@Valid @RequestBody dto: ClientVIPDTO): ResponseEntity<Client> {
        val clientVIP = ClientVIP(
            nom = dto.nom,
            prenom = dto.prenom,
            email = dto.email,
            telephone = dto.telephone,
            remise = dto.remise
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClientVIP(clientVIP))
    }

    @PutMapping("/{id}")
    fun updateClient(
        @PathVariable id: Int,
        @Valid @RequestBody dto: ClientDTO
    ): ResponseEntity<Client> {
        val updated = Client(
            nom = dto.nom,
            prenom = dto.prenom,
            email = dto.email,
            telephone = dto.telephone
        )
        return ResponseEntity.ok(clientService.updateClient(id, updated))
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: Int): ResponseEntity<Void> {
        clientService.deleteClient(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search/nom/{nom}")
    fun findByNom(@PathVariable nom: String): ResponseEntity<List<Client>> {
        return ResponseEntity.ok(clientService.findByNom(nom))
    }
}