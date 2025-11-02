package APiRes.Service

import APiRes.Exception.ResourceNotFoundException
import APiRes.Exception.BadRequestException
import APiRes.Models.Client
import APiRes.Models.ClientVIP
import APiRes.Repository.ClientRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ClientService(private val clientRepo: ClientRepository) {

    fun getAllClients(pageable: Pageable): Page<Client> =
        clientRepo.findAll(pageable)

    fun getClientById(id: Int): Client =
        clientRepo.findById(id)
            .orElseThrow { ResourceNotFoundException("Client avec l'ID $id introuvable") }

    fun addClient(client: Client): Client {
        if (client.nom.isBlank()) throw BadRequestException("Le nom ne peut pas être vide")
        if (client.email.isBlank()) throw BadRequestException("L'email ne peut pas être vide")
        return clientRepo.save(client)
    }

    fun addClientVIP(clientVIP: ClientVIP): Client {
        if (clientVIP.remise < 0 || clientVIP.remise > 1) {
            throw BadRequestException("La remise doit être entre 0 et 1")
        }
        return clientRepo.save(clientVIP)
    }

    fun updateClient(id: Int, updated: Client): Client {
        val existing = clientRepo.findById(id)
            .orElseThrow { ResourceNotFoundException("Client avec l'ID $id introuvable") }

        if (updated.nom.isBlank()) throw BadRequestException("Le nom ne peut pas être vide")
        if (updated.email.isBlank()) throw BadRequestException("L'email ne peut pas être vide")

        existing.nom = updated.nom
        existing.prenom = updated.prenom
        existing.email = updated.email
        existing.telephone = updated.telephone
        return clientRepo.save(existing)
    }

    fun deleteClient(id: Int) {
        if (!clientRepo.existsById(id)) {
            throw ResourceNotFoundException("Client avec l'ID $id introuvable")
        }
        clientRepo.deleteById(id)
    }

    fun findByNom(nom: String): List<Client> = clientRepo.findByNom(nom)
}