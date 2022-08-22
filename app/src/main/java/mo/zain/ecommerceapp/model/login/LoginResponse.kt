package mo.zain.ecommerceapp.model.login

data class LoginResponse(
    val data: LogiinItem,
    val message: String,
    val status: Boolean
)