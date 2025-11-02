package APiRes.DTO

import jakarta.validation.constraints.*
import java.time.LocalDate

// ✅ DTO pour créer une Réservation Online
data class ReservationOnlineDTO(
    @field:NotNull(message = "La date de début est obligatoire")
    @field:FutureOrPresent(message = "La date de début doit être aujourd'hui ou dans le futur")
    val dateDebut: LocalDate,

    @field:NotNull(message = "La date de fin est obligatoire")
    @field:Future(message = "La date de fin doit être dans le futur")
    val dateFin: LocalDate,

    @field:NotNull(message = "L'ID du client est obligatoire")
    val clientId: Int,

    @field:NotNull(message = "L'ID de la chambre est obligatoire")
    val chambreId: Int,

    @field:NotBlank(message = "La plateforme est obligatoire")
    val plateforme: String = "SiteWeb",

    @field:DecimalMin(value = "0.0", message = "La remise doit être positive")
    @field:DecimalMax(value = "1.0", message = "La remise ne peut pas dépasser 100%")
    val remise: Double = 0.0
)