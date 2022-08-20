package mo.zain.ecommerceapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mo.zain.ecommerceapp.model.*
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
    //

    private val _response=MutableLiveData<Data>()
    val responseItem:LiveData<Data>
        get() = _response


    init {
        register()
        Login()
        //getHome()
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

    fun getHome(token:String)=viewModelScope.launch {
        repository.getHome(token).let { response ->
            if (response.isSuccessful)
            {
                _response.postValue(response.body()?.data)
            }
        }

    }


}