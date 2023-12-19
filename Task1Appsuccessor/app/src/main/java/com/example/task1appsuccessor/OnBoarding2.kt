package com.example.task1appsuccessor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.task1appsuccessor.databinding.ActivityOnBoarding2Binding

class OnBoarding2 : AppCompatActivity() {
    lateinit var binding: ActivityOnBoarding2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoarding2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.startBtn.setOnClickListener {
            val intent = Intent(this,LogIn::class.java)
            startActivity(intent)
            finish()
        }
    }
}