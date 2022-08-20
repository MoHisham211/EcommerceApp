package mo.zain.ecommerceapp.model

data class Data(
    val ad: String,
    val banners: MutableList<Banner>,
    val products: List<Product>
)