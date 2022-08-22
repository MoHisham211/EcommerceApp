package mo.zain.ecommerceapp.model.product

data class ProductsResponse(
    val `data`: Data,
    val message: Any,
    val status: Boolean
)