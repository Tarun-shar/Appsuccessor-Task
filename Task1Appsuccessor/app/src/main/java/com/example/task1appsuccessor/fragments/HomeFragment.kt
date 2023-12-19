package com.example.task1appsuccessor.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitmvvm.api.ApiInterface
import com.example.retrofitmvvm.api.ApiUtilities
import com.example.retrofitmvvm.model.Meme
import com.example.retrofitmvvm.repository.MemesRepository
import com.example.retrofitmvvm.room.MemeDatabase
import com.example.retrofitmvvm.viewModel.MemesViewModel
import com.example.retrofitmvvm.viewModel.MemesViewModelFactory
import com.example.task1appsuccessor.ItemAdapter
import com.example.task1appsuccessor.Onboarding1
import com.example.task1appsuccessor.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

class HomeFragment : Fragment() {

    lateinit var firebaseAuth : FirebaseAuth

    private lateinit var memesViewModel: MemesViewModel

    lateinit var dataList: ArrayList<Meme>

    private lateinit var adapterObj: ItemAdapter

    lateinit var memesRepository: MemesRepository

    lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.logOut.setOnClickListener {
            val toast = Toast.makeText(context, "User LogOut!", Toast.LENGTH_SHORT)
            val toastView = toast.view
            toastView!!.setBackgroundResource(android.R.color.darker_gray)
            toast.show()
            firebaseAuth.signOut()
            startActivity(Intent(context,Onboarding1::class.java))
            requireActivity().finish()
        }

        val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)

        val database = MemeDatabase.getDatabase(requireContext())

        memesRepository = MemesRepository(apiInterface,database, requireContext())

        memesViewModel = ViewModelProvider(this, MemesViewModelFactory(memesRepository)).get(
            MemesViewModel::class.java)

        adapterObj = ItemAdapter(requireContext())
        binding.recyclerViewHome.adapter = adapterObj

        memesViewModel.memes.observe(requireActivity()) {

            adapterObj.submitList(it.data.memes)

//            Toast.makeText(context, "size  : ${it.data.memes.size}", Toast.LENGTH_LONG).show()

            it.data.memes.iterator().forEach { meme ->
                Log.d("TARUN", "name: ${meme.name}\n url: ${meme.url} ")
            }
        }

        //        SearchView Work
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterList(p0)
                return true
            }
        })

        return binding.root
    }

//    searchView Function
    private fun filterList(query: String?) {
        if(query != null){
            val filterList = ArrayList<Meme>()
            for(i in dataList){
                if(i.name.toLowerCase(Locale.ROOT).contains(query)){
                    filterList.add(i)
                }
            }
            if(filterList.isEmpty()){
                val toast = Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT)
                val toastView = toast.view
                toastView!!.setBackgroundResource(android.R.color.darker_gray)
                toast.show()
            }
            else{
                adapterObj.submitList(filterList)
            }
        }
    }
}

