package mo.zain.ecommerceapp.model

data class RegisterResponse(
    val `data`: RegistrationItem,
    val message: String,
    val status: Boolean
)