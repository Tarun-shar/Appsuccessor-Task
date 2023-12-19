package com.example.task1appsuccessor

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.task1appsuccessor.databinding.ActivityOnboarding1Binding

class Onboarding1 : AppCompatActivity() {
    lateinit var binding: ActivityOnboarding1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboarding1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.nextBtn.setOnClickListener {
            val intent = Intent(this,OnBoarding2::class.java)
            startActivity(intent)
            finish()
        }

        binding.skip.setOnClickListener {
            val intent = Intent(this,LogIn::class.java)
            startActivity(intent)
            finish()
        }
    }
}