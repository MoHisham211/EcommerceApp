package mo.zain.ecommerceapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mo.zain.ecommerceapp.model.category.DataX
import mo.zain.ecommerceapp.model.favorite.FavoriteRespo
import mo.zain.ecommerceapp.model.home.Data
import mo.zain.ecommerceapp.model.home.Product
import mo.zain.ecommerceapp.model.login.LoginResponse
import mo.zain.ecommerceapp.model.product.ProductsResponse
import mo.zain.ecommerceapp.model.registration.RegisterResponse
import mo.zain.ecommerceapp.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel

@Inject constructor(private val repository: UserRepository):ViewModel(){

    //
    private val _registResponse:MutableLiveData<RegisterResponse> = MutableLiveData()
    var registerDetails: RegisterResponse? = null
    fun register():MutableLiveData<RegisterResponse>{
        return _registResponse
    }

    //
    private val _loginResponse:MutableLiveData<LoginResponse> = MutableLiveData()
    var loginDetails: LoginResponse? =null
    fun Login():MutableLiveData<LoginResponse>{
        return _loginResponse
    }

    //
    private val _searchResponse:MutableLiveData<ProductsResponse> = MutableLiveData()
    var searchDetails:ProductsResponse? =null
    fun search():MutableLiveData<ProductsResponse>{
        return _searchResponse
    }

    private val _getCatDetailsResponse:MutableLiveData<List<Product>> = MutableLiveData()
    val categDetails:LiveData<List<Product>>
    get()=_getCatDetailsResponse

    private val _setFavourDetailsResponse:MutableLiveData<FavoriteRespo> = MutableLiveData()
    var setFavorite:FavoriteRespo? =null
    fun favorite():MutableLiveData<FavoriteRespo>{
        return _setFavourDetailsResponse
    }





    //

    private val _response=MutableLiveData<Data>()
    val responseItem:LiveData<Data>
    get() = _response

    private val _responseCategory=MutableLiveData<DataX>()
    val responseCategory:LiveData<DataX>
    get() = _responseCategory


    init {
        register()
        Login()
        getCategory()
        search()
        favorite()
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


    suspend fun searchRepo(text:String){
        searchDetails=repository.searchProduct(text)
        _searchResponse.value=searchDetails
    }

    suspend fun catDetails(id:Int)=viewModelScope.launch {
        repository.getCatDetails(id).let { it->
            if (it.body()!!.status){
                _getCatDetailsResponse.postValue(it.body()?.data!!.data)
            }

        }
    }

    fun getHome(token:String)=viewModelScope.launch {
        repository.getHome(token).let { response ->
            if (response.isSuccessful)
            {
                _response.postValue(response.body()?.data)
            }
        }

    }
    fun getCategory()=viewModelScope.launch {
        repository.getCategory().let {it->
            if (it.body()!!.status){
                _responseCategory.postValue(it.body()!!.data)
            }
        }
    }

    suspend fun favoriteRespo(token: String,product_id: Int)
    {
        setFavorite=repository.favorFun(token,product_id)
        _setFavourDetailsResponse.value=setFavorite
    }




}