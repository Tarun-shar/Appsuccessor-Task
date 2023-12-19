package com.example.task1appsuccessor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.task1appsuccessor.databinding.ActivityMainBinding
import com.example.task1appsuccessor.fragments.APIFragment
import com.example.task1appsuccessor.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.api -> {
                    replaceFragment(APIFragment())
                    true
                }
                else -> false
            }
        }
//        initially give default fragment
        replaceFragment(HomeFragment())
        //    set frame layout
    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_Container,fragment).commit()
    }
}