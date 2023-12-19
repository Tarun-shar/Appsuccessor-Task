package com.example.task1appsuccessor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Splash2 : AppCompatActivity() {
    lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash2)

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        firebaseAuth = FirebaseAuth.getInstance()

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            // The user is already signed in, navigate to MainActivity
            val toast = Toast.makeText(this, "User Already Login", Toast.LENGTH_SHORT)
            val toastView = toast.view
            toastView!!.setBackgroundResource(android.R.color.darker_gray)
            toast.show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
        }
        else{
            val obj: Thread = object : Thread(){
                override fun run() {
                    try {
                        sleep(1500)
                    }
                    catch (e:Exception){
                        e.printStackTrace()
                    }
                    finally {
                        val intent  = Intent(this@Splash2 , Onboarding1::class.java)
                        startActivity(intent)
                        finish()

                    }
                }
            }
            obj.start()
        }
    }
}