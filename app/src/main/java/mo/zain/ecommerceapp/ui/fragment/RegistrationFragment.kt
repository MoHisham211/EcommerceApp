package mo.zain.ecommerceapp.ui.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mo.zain.ecommerceapp.R
import mo.zain.ecommerceapp.viewModel.UserViewModel

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    lateinit var userName: TextInputEditText
    lateinit var userEmail: TextInputEditText
    lateinit var userPhone:TextInputEditText
    lateinit var userPassword:TextInputEditText
    lateinit var toSignIn:Button
    lateinit var progressBar:ProgressBar
    lateinit var agreeCheck:CheckBox
    lateinit var viewContant:View
    lateinit var toSignUp:TextView
    private val viewModel:UserViewModel by viewModels()

    //
    var saveToken:SharedPreferences ? =null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_registration, container, false)

        userName=view.findViewById(R.id.userName)
        userEmail=view.findViewById(R.id.userEmail)
        userPhone=view.findViewById(R.id.userPhone)
        userPassword=view.findViewById(R.id.userPassword)
        toSignIn=view.findViewById(R.id.toSignIn)
        progressBar=view.findViewById(R.id.progressBar)
        agreeCheck=view.findViewById(R.id.agreeCheck)
        viewContant=view.findViewById(R.id.viewContant)
        toSignUp=view.findViewById(R.id.toSignUp)

        toSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        toSignIn.setOnClickListener {
            checkData()
        }

        saveToken=requireActivity().getSharedPreferences("Token", MODE_PRIVATE)

        return view
    }

    private fun checkData() {
        if (userName.text.isNullOrEmpty())
        {
            userName.setError("Please Enter Your Name!")
            return
        }else if (userEmail.text.isNullOrEmpty())
        {
            userEmail.setError("Please Enter Your Email!")
            return
        }else if (userPhone.text.isNullOrEmpty()){
            userPhone.setError("Please Enter Your Phone Number!")
            return
        }else if (userPassword.text.isNullOrEmpty()){
            userPassword.setError("Please Enter Your Password!")
            return
        }else if(!agreeCheck.isChecked)
        {
            Snackbar.make(viewContant, "Please agree to all conditions", Snackbar.LENGTH_LONG)
                .setAction("CLOSE") {

                }
                .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
                .show()
        }
        else{
                registerUser()
        }
    }

    private fun registerUser() {
        progressBar.visibility=View.VISIBLE

        lifecycleScope.launch {
            viewModel.RegisterRepo(userName.text.toString(),userPhone.text.toString()
                ,userEmail.text.toString(),userPassword.text.toString(),"")

            if (viewModel.register().value?.status==true)
            {
                //Check Here
                Toast.makeText(requireContext(), ""+
                        viewModel.register().value?.message+"\n"+
                        viewModel.register().value?.data!!.token+"\n"
                        +viewModel.register().value?.data!!.id+"\n", Toast.LENGTH_SHORT).show()

                //
                var editor:SharedPreferences.Editor=saveToken!!.edit()
                editor.putString("Token",
                    viewModel.register().value?.data!!.token.trim()).apply()

                progressBar.visibility=View.INVISIBLE


            }else
            {
                Toast.makeText(requireContext(), ""+
                        viewModel.register().value?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}