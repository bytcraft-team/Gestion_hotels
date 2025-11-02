package APiRes.DTO

import jakarta.validation.constraints.*

// ✅ DTO pour créer/mettre à jour une Suite
data class ChambreSuiteDTO(
    @field:Min(value = 1, message = "Le numéro de chambre doit être supérieur à 0")
    val numero: Int,

    @field:DecimalMin(value = "0.0", message = "Le prix doit être positif")
    val prix: Double,

    @field:NotBlank(message = "Le nom de la suite est obligatoire")
    @field:Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    val suiteNom: String,

    @field:Min(value = 1, message = "Le nombre de pièces doit être au moins 1")
    @field:Max(value = 20, message = "Le nombre de pièces ne peut pas dépasser 20")
    val nombrePieces: Int = 2,

    val jacuzzi: Boolean = false
)