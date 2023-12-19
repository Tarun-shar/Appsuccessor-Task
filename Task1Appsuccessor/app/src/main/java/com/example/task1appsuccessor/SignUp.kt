package com.example.task1appsuccessor

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import com.example.task1appsuccessor.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    private lateinit var firebaseauth: FirebaseAuth

    lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        firebaseauth = FirebaseAuth.getInstance()

        binding.goLogIn.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()
        }

        //        for email
        emailFocusListener()
//        for password
        passwordFocusListener()

        binding.btnSignUp.setOnClickListener {
            btnWork()
        }
    }

    private fun passwordFocusListener()
    {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding.passwordEditText.text.toString()
        if(passwordText.length < 8){
            return "Minimum 8 Character Password"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Must Contain 1 Upper-Case Character"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Must Contain 1 Lower-Case Character"
        }
        if (!passwordText.matches(".*[@#$%^&+=].*".toRegex()))
        {
            return "Must Contain 1 Special Character (.*[@#$%^&+=].*)"
        }
        return null
    }

    private fun emailFocusListener()
    {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.emailEditText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Invalid Email Address"
        }
        return null
    }

    private fun btnWork() {

        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null

        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            if(validEmail && validPassword){
                firebaseauth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            val toast = Toast.makeText(this, "SignUp Successful", Toast.LENGTH_SHORT)
                            val toastView = toast.view
                            toastView!!.setBackgroundResource(R.color.darker_gray)
                            toast.show()

                            val intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            val toast = Toast.makeText(this, "Authentication Failed!", Toast.LENGTH_SHORT)
                            val toastView = toast.view
                            toastView!!.setBackgroundResource(R.color.darker_gray)
                            toast.show()
                        }
                    }

//                resetForm()
            }
            else{
                val toast = Toast.makeText(this, "Authentication Failed!", Toast.LENGTH_SHORT)
                val toastView = toast.view
                toastView!!.setBackgroundResource(R.color.darker_gray)
                toast.show()

            }

        }
        else{
            if(email.isEmpty() && password.isEmpty()){
                val toast = Toast.makeText(this, "Please fill the all fields!", Toast.LENGTH_SHORT)
                val toastView = toast.view
                toastView!!.setBackgroundResource(R.color.darker_gray)
                toast.show()
            }
            else if(email.isEmpty()){
                val toast = Toast.makeText(this, "Please fill E-mail field!", Toast.LENGTH_SHORT)
                val toastView = toast.view
                toastView!!.setBackgroundResource(R.color.darker_gray)
                toast.show()
            }
            else{
                val toast = Toast.makeText(this, "Please fill Password field!", Toast.LENGTH_SHORT)
                val toastView = toast.view
                toastView!!.setBackgroundResource(R.color.darker_gray)
                toast.show()
            }

        }

    }

//    private fun resetForm() {
//        binding.emailEditText.text = null
//        binding.passwordEditText.text = null
//    }
}