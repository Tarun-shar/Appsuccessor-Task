package com.example.task1appsuccessor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.task1appsuccessor.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding:ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val obj = intent

        val title = obj.getStringExtra("title")
        val image = obj.getStringExtra("Image")
        val des = obj.getStringExtra("description")

        binding.itemTitle.text = title
        binding.itemDes.text = des
        Glide.with(this).load(image).into(binding.itemImage)
    }
}