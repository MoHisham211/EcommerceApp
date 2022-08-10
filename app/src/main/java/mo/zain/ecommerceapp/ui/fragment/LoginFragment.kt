package mo.zain.ecommerceapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mo.zain.ecommerceapp.R
import mo.zain.ecommerceapp.ui.AuthenticationActivity
import mo.zain.ecommerceapp.ui.MainActivity
import mo.zain.ecommerceapp.viewModel.UserViewModel


@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var toSignUp:TextView
    lateinit var toSignIn:Button
    lateinit var userEmail: TextInputEditText
    lateinit var userPassword:TextInputEditText
    lateinit var progressBar2:ProgressBar
    var saveToken:SharedPreferences ? =null

    private val viewModel:UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_login, container, false)
        toSignUp=view.findViewById(R.id.toSignUp)
        toSignIn=view.findViewById(R.id.toSignIn)
        userEmail=view.findViewById(R.id.userEmail)
        userPassword=view.findViewById(R.id.userPassword)
        progressBar2=view.findViewById(R.id.progressBar2)
        saveToken=requireActivity().getSharedPreferences("Token", Context.MODE_PRIVATE)


        toSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        toSignIn.setOnClickListener {
            checkData()
        }
        return view
    }

    private fun checkData() {
        if (userEmail.text.isNullOrEmpty())
        {
            userEmail.setError("Please Enter Your Email!")
            return
        }else if (userPassword.text.isNullOrEmpty()){
            userPassword.setError("Please Enter Your Password!")
            return
        }else {
            loginUser()
        }

    }

    private fun loginUser() {
        progressBar2.visibility=View.VISIBLE
        lifecycleScope.launch {
            viewModel.LoginRepo(userEmail.text.toString(),userPassword.text.toString())
            if (viewModel.Login().value?.status==true){

                Toast.makeText(requireContext(), ""+
                        viewModel.Login().value?.message+"\n"
                    +viewModel.Login().value?.data?.token
                    , Toast.LENGTH_SHORT).show()


                var editor: SharedPreferences.Editor=saveToken!!.edit()
                editor.putString("Token",
                    viewModel.Login().value?.data!!.token.trim()).apply()

                progressBar2.visibility=View.INVISIBLE
                val intent=Intent(requireContext(),MainActivity::class.java)
                requireActivity().startActivity(intent)
                requireActivity().finish()
            }else
            {
                Toast.makeText(requireContext(), ""+
                        viewModel.Login().value?.message, Toast.LENGTH_SHORT).show()
            }

        }
    }


}