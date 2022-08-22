package mo.zain.ecommerceapp.model.home

data class Data(
    val ad: String,
    val banners: MutableList<Banner>,
    val products: List<Product>
)