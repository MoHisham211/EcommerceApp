package mo.zain.ecommerceapp.model

data class LoginResponse(
    val data: LogiinItem,
    val message: String,
    val status: Boolean
)