package com.example.task1appsuccessor

import android.R
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.task1appsuccessor.databinding.ActivityLogInBinding
import com.example.task1appsuccessor.databinding.ForgotPasswordDialogBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class LogIn : AppCompatActivity() {

    lateinit var firebaseAuth:FirebaseAuth

    lateinit var dialog:ForgotPasswordDialogBinding

    lateinit var binding:ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        firebaseAuth = FirebaseAuth.getInstance()

        val currentUser = firebaseAuth.currentUser

        binding.btnLogIn.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener{ task ->
                            if(task.isSuccessful){
                                val toast = Toast.makeText(this, "LogIn Successful", Toast.LENGTH_SHORT)
                                val toastView = toast.view
                                toastView!!.setBackgroundResource(R.color.darker_gray)
                                toast.show()
                                val intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else{
                                if(currentUser == null){
                                    val toast = Toast.makeText(this, "Not Valid User!", Toast.LENGTH_SHORT)
                                    val toastView = toast.view
                                    toastView!!.setBackgroundResource(R.color.darker_gray)
                                    toast.show()
                                }
                                else{
                                    val toast = Toast.makeText(this, "Authentication Failed!", Toast.LENGTH_SHORT)
                                    val toastView = toast.view
                                    toastView!!.setBackgroundResource(R.color.darker_gray)
                                    toast.show()
                                }
                            }
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
                else if(password.isEmpty()){
                    val toast = Toast.makeText(this, "Please fill Password field!", Toast.LENGTH_SHORT)
                    val toastView = toast.view
                    toastView!!.setBackgroundResource(R.color.darker_gray)
                    toast.show()
                }

            }
        }

        binding.goSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }

        binding.forgotPassword.setOnClickListener {
            showForgotPasswordDialog()
        }
    }

    private fun showForgotPasswordDialog() {
        dialog = ForgotPasswordDialogBinding.inflate(LayoutInflater.from(this))
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialog.root)
            .create()
        alertDialog.show()
        dialog.emailForgot.requestFocus()
        dialog.backToLogin.setOnClickListener {
            val intent = Intent(this,LogIn::class.java)
            startActivity(intent)
            finish()
        }
        dialog.btnForgotPassword.setOnClickListener {
            val email = dialog.emailForgot.text.toString()
            alertDialog.dismiss()
            resetPassword(email)
        }
    }

    private fun resetPassword(email: String) {
        lifecycleScope.launch {
            try{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
                val toast = Toast.makeText(this@LogIn, "Please check your email & reset!", Toast.LENGTH_SHORT)
                val toastView = toast.view
                toastView!!.setBackgroundResource(R.color.darker_gray)
                toast.show()
            }
            catch(e : Exception){
                val toast = Toast.makeText(this@LogIn, e.message.toString(), Toast.LENGTH_SHORT)
                val toastView = toast.view
                toastView!!.setBackgroundResource(R.color.darker_gray)
                toast.show()
            }
        }
    }
}