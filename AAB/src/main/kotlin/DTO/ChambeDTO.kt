package APiRes.DTO

import jakarta.validation.constraints.*

// ✅ DTO pour créer/mettre à jour une Chambre
data class ChambreDTO(
    @field:Min(value = 1, message = "Le numéro de chambre doit être supérieur à 0")
    @field:Max(value = 9999, message = "Le numéro de chambre ne peut pas dépasser 9999")
    val numero: Int,

    @field:DecimalMin(value = "0.0", message = "Le prix doit être positif")
    @field:DecimalMax(value = "999999.99", message = "Le prix est trop élevé")
    val prix: Double,

    @field:NotBlank(message = "Le type de chambre est obligatoire")
    @field:Pattern(regexp = "^(SIMPLE|SUITE)$", message = "Type de chambre invalide")
    val typeChambre: String
)