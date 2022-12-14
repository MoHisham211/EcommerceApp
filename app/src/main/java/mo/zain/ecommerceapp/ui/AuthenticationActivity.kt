package mo.zain.ecommerceapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import mo.zain.ecommerceapp.R

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    /*
                    USER
        TODO-->   1-->Registration   Done
        TODO-->   2-->Login   Done
        TODO-->   1-->Logout   Wait
        TODO-->   1-->Profile   Wait
        TODO-->   1-->Update Profile   Wait
        TODO-->   1-->Reset Password   Wait
                    Home
        TODO-->   1-->Login   Done
        TODO-->   1-->Login   Done
     */

    var myShared:SharedPreferences ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        supportActionBar?.hide()
        myShared=getSharedPreferences("Token", Context.MODE_PRIVATE)

        if (!myShared?.getString("Token","").isNullOrEmpty())
        {
            startActivity(Intent(this@AuthenticationActivity,MainActivity::class.java))
            finish()
        }

        /*
            Important in xml MainActivity-----> app:defaultNavHost="true"
            true --->تعني ان لما ارجع لازم اعدي علي كل حاجه فتحتها
            false --> تعني اني لما اعمل back اخرج من الكل

         */


        setupActionBarWithNavController(findNavController(R.id.fragmentContainerView))

    }
    override fun onSupportNavigateUp(): Boolean {
        var navigate = findNavController(R.id.fragmentContainerView)
        return navigate.navigateUp() || super.onSupportNavigateUp()
    }

}