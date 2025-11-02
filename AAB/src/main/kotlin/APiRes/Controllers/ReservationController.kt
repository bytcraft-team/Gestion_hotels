package APiRes.Controllers

import APiRes.DTO.ReservationDTO
import APiRes.DTO.ReservationOnlineDTO
import APiRes.Models.Reservation
import APiRes.Service.ReservationService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/reservations")
class ReservationController(private val reservationService: ReservationService) {

    @GetMapping
    fun getAllReservations(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "reservationId") sortBy: String
    ): ResponseEntity<Page<Reservation>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortBy))
        return ResponseEntity.ok(reservationService.getAllReservations(pageable))
    }

    @GetMapping("/{id}")
    fun getReservationById(@PathVariable id: Int): ResponseEntity<Reservation> {
        return ResponseEntity.ok(reservationService.getReservationById(id))
    }

    @PostMapping
    fun addReservation(@Valid @RequestBody dto: ReservationDTO): ResponseEntity<Reservation> {
        val reservation = reservationService.createReservation(
            dateDebut = dto.dateDebut,
            dateFin = dto.dateFin,
            clientId = dto.clientId,
            chambreId = dto.chambreId,
            employeId = dto.employeId
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation)
    }

    @PostMapping("/online")
    fun addOnlineReservation(@Valid @RequestBody dto: ReservationOnlineDTO): ResponseEntity<Reservation> {
        val reservation = reservationService.createOnlineReservation(
            dateDebut = dto.dateDebut,
            dateFin = dto.dateFin,
            clientId = dto.clientId,
            chambreId = dto.chambreId,
            plateforme = dto.plateforme,
            remise = dto.remise
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation)
    }

    @PutMapping("/{id}/confirmer")
    fun confirmerReservation(
        @PathVariable id: Int,
        @RequestParam(required = false) employeId: Int?
    ): ResponseEntity<Reservation> {
        return ResponseEntity.ok(reservationService.confirmerReservation(id, employeId))
    }

    @PutMapping("/{id}/annuler")
    fun annulerReservation(
        @PathVariable id: Int,
        @RequestParam(required = false) employeId: Int?
    ): ResponseEntity<Reservation> {
        return ResponseEntity.ok(reservationService.annulerReservation(id, employeId))
    }

    @GetMapping("/{id}/montant")
    fun calculerMontant(@PathVariable id: Int): ResponseEntity<Map<String, Double>> {
        val montant = reservationService.calculerMontant(id)
        return ResponseEntity.ok(mapOf("montant" to montant))
    }

    @DeleteMapping("/{id}")
    fun deleteReservation(@PathVariable id: Int): ResponseEntity<Void> {
        reservationService.deleteReservation(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/statut/{statut}")
    fun getByStatut(@PathVariable statut: String): ResponseEntity<List<Reservation>> {
        return ResponseEntity.ok(reservationService.getReservationsByStatus(statut))
    }

    @GetMapping("/client/{clientId}")
    fun getByClient(@PathVariable clientId: Int): ResponseEntity<List<Reservation>> {
        return ResponseEntity.ok(reservationService.getReservationsByClient(clientId))
    }

    @GetMapping("/chambre/{chambreId}")
    fun getByChambre(@PathVariable chambreId: Int): ResponseEntity<List<Reservation>> {
        return ResponseEntity.ok(reservationService.getReservationsByChambre(chambreId))
    }

    @GetMapping("/dates")
    fun getByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) start: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) end: LocalDate
    ): ResponseEntity<List<Reservation>> {
        return ResponseEntity.ok(reservationService.getReservationsBetweenDates(start, end))
    }
}