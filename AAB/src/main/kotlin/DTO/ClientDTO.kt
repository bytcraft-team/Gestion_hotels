package APiRes.DTO

import jakarta.validation.constraints.*

// ✅ DTO pour créer/mettre à jour un Client
data class ClientDTO(
    @field:NotBlank(message = "Le nom est obligatoire")
    @field:Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    val nom: String,

    @field:NotBlank(message = "Le prénom est obligatoire")
    @field:Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    val prenom: String,

    @field:NotBlank(message = "L'email est obligatoire")
    @field:Email(message = "Format d'email invalide")
    val email: String,

    @field:NotBlank(message = "Le téléphone est obligatoire")
    @field:Pattern(regexp = "^[0-9+\\-\\s()]{10,20}$", message = "Format de téléphone invalide")
    val telephone: String
)