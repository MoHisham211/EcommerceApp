package mo.zain.ecommerceapp.repository

import mo.zain.ecommerceapp.api.ApiService
import javax.inject.Inject

class UserRepository

@Inject constructor(private val apiService: ApiService) {
    suspend fun registUser(name:String,
                           phone:String,
                           email:String,
                           password:String,image:String)=apiService.registUser(name,phone,email,password,image)


    suspend fun loginUser(email: String,password: String)=apiService.loginUser(email, password)

    suspend fun getHome(token:String)=apiService.getHome(token)

    suspend fun getCategory()=apiService.getCategory()

    suspend fun searchProduct(text:String)=apiService.searchProduct(text)

    suspend fun getCatDetails(id:Int)=apiService.getCatDetails(id)

    suspend fun favorFun(token: String,product_id:Int)=apiService.favorFun(token,product_id)
}