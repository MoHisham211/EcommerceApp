package mo.zain.ecommerceapp.model.registration

data class RegisterResponse(
    val `data`: RegistrationItem,
    val message: String,
    val status: Boolean
)