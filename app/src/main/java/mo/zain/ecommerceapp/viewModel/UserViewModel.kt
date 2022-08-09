package mo.zain.ecommerceapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mo.zain.ecommerceapp.model.LoginResponse
import mo.zain.ecommerceapp.model.RegisterResponse
import mo.zain.ecommerceapp.model.RegistrationItem
import mo.zain.ecommerceapp.repository.UserRepository
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UserViewModel

@Inject constructor(private val repository: UserRepository):ViewModel(){

    //
    private val _registResponse:MutableLiveData<RegisterResponse> = MutableLiveData()
    var registerDetails:RegisterResponse? = null

    fun register():MutableLiveData<RegisterResponse>{
        return _registResponse
    }

    //
    private val _loginResponse:MutableLiveData<LoginResponse> = MutableLiveData()
    var loginDetails:LoginResponse? =null
    fun Login():MutableLiveData<LoginResponse>{
        return _loginResponse
    }


    init {
        register()
        Login()
    }

    //Call In Fragment S00N -->
    suspend fun RegisterRepo( name:String,phone:String,email:String,password:String,image:String){
        registerDetails=repository.registUser(name,phone,email,password,image )
        _registResponse.value=registerDetails
    }

    suspend fun LoginRepo(email: String,password: String)
    {
        loginDetails=repository.loginUser(email, password)
        _loginResponse.value=loginDetails
    }
}