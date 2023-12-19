package com.example.task1appsuccessor.fragments

import android.R
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
import com.example.task1appsuccessor.databinding.FragmentAPIBinding
import java.util.Locale

class APIFragment : Fragment() {

    private lateinit var memesViewModel: MemesViewModel

    lateinit var dataList: ArrayList<Meme>

    private lateinit var adapterObj: ItemAdapter

    lateinit var memesRepository: MemesRepository

    lateinit var binding: FragmentAPIBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAPIBinding.inflate(layoutInflater,container,false)

        val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)

        val database = MemeDatabase.getDatabase(requireContext())

        memesRepository = MemesRepository(apiInterface,database, requireContext())

        memesViewModel = ViewModelProvider(this, MemesViewModelFactory(memesRepository)).get(MemesViewModel::class.java)

        adapterObj = ItemAdapter(requireContext())
        binding.recyclerView.adapter = adapterObj

//        memesViewModel.memes.observe(requireActivity()) {
//
//            adapterObj.submitList(it.data.memes)
//
////            Toast.makeText(context, "size  : ${it.data.memes.size}", Toast.LENGTH_LONG).show()
//
//            it.data.memes.iterator().forEach { meme ->
//                Log.d("TARUN", "name: ${meme.name}\n url: ${meme.url} ")
//            }
//        }

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

//    private fun filterList(query: String?) {
//        if(query != null){
//            val filterList = ArrayList<Meme>()
//            for(i in dataList){
//                if(i.name.toLowerCase(Locale.ROOT).contains(query)){
//                    filterList.add(i)
//                }
//            }
//            if(filterList.isEmpty()){
//                val toast = Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT)
//                val toastView = toast.view
//                toastView!!.setBackgroundResource(R.color.darker_gray)
//                toast.show()
//            }
//            else{
//                adapterObj.submitList(filterList)
//            }
//        }
//    }
}

