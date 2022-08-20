package mo.zain.ecommerceapp.api

import mo.zain.ecommerceapp.model.HomeResponse
import mo.zain.ecommerceapp.model.LoginResponse
import mo.zain.ecommerceapp.model.RegisterResponse
import mo.zain.ecommerceapp.model.RegistrationItem
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @POST("register")
    suspend fun registUser(@Query("name") name:String,
                           @Query("phone") phone:String,
                           @Query("email") email:String,
                           @Query("password") password:String
    ,@Query("image") image: String,
    ): RegisterResponse


    @POST("login")
    suspend fun loginUser(@Query("email")email:String
                          ,@Query("password") password:String):LoginResponse

    @GET("home")
    suspend fun getHome(@Header("Authorization") token:String):Response<HomeResponse>
}