package APiRes.DTO

import jakarta.validation.constraints.*

// ✅ DTO pour créer/mettre à jour un Employee
data class EmployeeDTO(
    @field:NotBlank(message = "Le nom est obligatoire")
    @field:Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    val nom: String,

    @field:NotBlank(message = "Le poste est obligatoire")
    @field:Size(min = 2, max = 50, message = "Le poste doit contenir entre 2 et 50 caractères")
    val poste: String,

    @field:DecimalMin(value = "0.0", message = "Le salaire doit être positif")
    @field:DecimalMax(value = "999999.99", message = "Le salaire est trop élevé")
    val salaire: Double
)