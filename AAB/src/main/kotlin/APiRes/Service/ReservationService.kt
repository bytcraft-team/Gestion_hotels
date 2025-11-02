package APiRes.Service

import APiRes.Exception.ResourceNotFoundException
import APiRes.Exception.BadRequestException
import APiRes.Models.*
import APiRes.Repository.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReservationService(
    private val reservationRepo: ReservationRepository,
    private val clientRepo: ClientRepository,
    private val chambreRepo: ChambreRepository,
    private val employeeRepo: EmployeeRepository
) {

    fun getAllReservations(pageable: Pageable): Page<Reservation> =
        reservationRepo.findAll(pageable)

    fun getReservationById(id: Int): Reservation =
        reservationRepo.findById(id)
            .orElseThrow { ResourceNotFoundException("Réservation avec l'ID $id introuvable") }

    fun addReservation(reservation: Reservation): Reservation {
        validateReservationDates(reservation.dateDebut, reservation.dateFin)
        return reservationRepo.save(reservation)
    }

    fun createReservation(
        dateDebut: LocalDate,
        dateFin: LocalDate,
        clientId: Int,
        chambreId: Int,
        employeId: Int?
    ): Reservation {
        validateReservationDates(dateDebut, dateFin)

        val client = clientRepo.findById(clientId)
            .orElseThrow { ResourceNotFoundException("Client avec l'ID $clientId introuvable") }

        val chambre = chambreRepo.findById(chambreId)
            .orElseThrow { ResourceNotFoundException("Chambre avec l'ID $chambreId introuvable") }

        val employe = employeId?.let {
            employeeRepo.findById(it)
                .orElseThrow { ResourceNotFoundException("Employé avec l'ID $it introuvable") }
        }

        val reservation = Reservation(
            dateDebut = dateDebut,
            dateFin = dateFin,
            client = client,
            chambre = chambre,
            employe = employe
        )

        return reservationRepo.save(reservation)
    }

    fun addOnlineReservation(resOnline: ReservationOnline): Reservation {
        validateReservationDates(resOnline.dateDebut, resOnline.dateFin)
        if (resOnline.remise < 0 || resOnline.remise > 1) {
            throw BadRequestException("La remise doit être entre 0 et 1")
        }
        return reservationRepo.save(resOnline)
    }

    fun createOnlineReservation(
        dateDebut: LocalDate,
        dateFin: LocalDate,
        clientId: Int,
        chambreId: Int,
        plateforme: String,
        remise: Double
    ): ReservationOnline {
        validateReservationDates(dateDebut, dateFin)

        if (remise < 0 || remise > 1) {
            throw BadRequestException("La remise doit être entre 0 et 1")
        }

        val client = clientRepo.findById(clientId)
            .orElseThrow { ResourceNotFoundException("Client avec l'ID $clientId introuvable") }

        val chambre = chambreRepo.findById(chambreId)
            .orElseThrow { ResourceNotFoundException("Chambre avec l'ID $chambreId introuvable") }

        val reservation = ReservationOnline(
            dateDebut = dateDebut,
            dateFin = dateFin,
            client = client,
            chambre = chambre,
            plateforme = plateforme,
            remise = remise
        )

        return reservationRepo.save(reservation)
    }

    fun updateReservation(id: Int, updated: Reservation): Reservation {
        val existing = reservationRepo.findById(id)
            .orElseThrow { ResourceNotFoundException("Réservation avec l'ID $id introuvable") }

        validateReservationDates(updated.dateDebut, updated.dateFin)

        existing.dateDebut = updated.dateDebut
        existing.dateFin = updated.dateFin
        existing.statut = updated.statut
        existing.client = updated.client
        existing.chambre = updated.chambre
        existing.employe = updated.employe
        return reservationRepo.save(existing)
    }

    fun deleteReservation(id: Int) {
        if (!reservationRepo.existsById(id)) {
            throw ResourceNotFoundException("Réservation avec l'ID $id introuvable")
        }
        reservationRepo.deleteById(id)
    }

    fun confirmerReservation(id: Int, employeId: Int?): Reservation {
        val reservation = getReservationById(id)

        val employe = employeId?.let {
            employeeRepo.findById(it)
                .orElseThrow { ResourceNotFoundException("Employé avec l'ID $it introuvable") }
        }

        reservation.confirmer(employe)
        return reservationRepo.save(reservation)
    }

    fun annulerReservation(id: Int, employeId: Int?): Reservation {
        val reservation = getReservationById(id)

        val employe = employeId?.let {
            employeeRepo.findById(it)
                .orElseThrow { ResourceNotFoundException("Employé avec l'ID $it introuvable") }
        }

        reservation.annuler(employe)
        return reservationRepo.save(reservation)
    }

    fun calculerMontant(id: Int): Double {
        val reservation = getReservationById(id)
        return reservation.calculerMontant()
    }

    fun getReservationsByStatus(statut: String): List<Reservation> =
        reservationRepo.findByStatut(statut)

    fun getReservationsByClient(clientId: Int): List<Reservation> =
        reservationRepo.findByClient_ClientId(clientId)

    fun getReservationsByChambre(chambreId: Int): List<Reservation> =
        reservationRepo.findByChambre_ChambreId(chambreId)

    fun getReservationsBetweenDates(start: LocalDate, end: LocalDate): List<Reservation> =
        reservationRepo.findByDateDebutBetween(start, end)

    private fun validateReservationDates(debut: LocalDate, fin: LocalDate) {
        if (fin.isBefore(debut) || fin.isEqual(debut)) {
            throw BadRequestException("La date de fin doit être après la date de début")
        }
        if (debut.isBefore(LocalDate.now())) {
            throw BadRequestException("La date de début ne peut pas être dans le passé")
        }
    }
}