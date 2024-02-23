package mo.zain.ecommerceapp.model.favorite

data class Product(
    val discount: Int,
    val id: Int,
    val image: String,
    val old_price: Int,
    val price: Int
)