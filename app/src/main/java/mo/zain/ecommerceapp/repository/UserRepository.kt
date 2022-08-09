package mo.zain.ecommerceapp.repository

import mo.zain.ecommerceapp.api.ApiService
import mo.zain.ecommerceapp.model.RegistrationItem
import okhttp3.MultipartBody
import retrofit2.http.Part
import retrofit2.http.Query
import javax.inject.Inject

class UserRepository

@Inject constructor(private val apiService: ApiService) {
    suspend fun registUser(name:String,
                           phone:String,
                           email:String,
                           password:String,image:String)=apiService.registUser(name,phone,email,password,image)


    suspend fun loginUser(email: String,password: String)=apiService.loginUser(email, password)
}