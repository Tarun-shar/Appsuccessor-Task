package com.example.task1appsuccessor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitmvvm.model.Meme
import com.squareup.picasso.Picasso

class ItemAdapter(public val context: Context) : ListAdapter<Meme , ItemAdapter.ItemViewHolder>(DiffUtil()) {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Meme>(){
        override fun areItemsTheSame(oldItem: Meme, newItem: Meme): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Meme, newItem: Meme): Boolean {
            return oldItem == newItem
        }

    }

    class ItemViewHolder(view:View): RecyclerView.ViewHolder(view){
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val textView = view.findViewById<TextView>(R.id.name)
        val card = view.findViewById<CardView>(R.id.cardView)
//
        fun bind(meme: Meme){
            textView.text = meme.name
            Picasso.get().load(meme.url).into(imageView)
//
//   card.setOnClickListener {
//        Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
////            val intent = Intent(context , DetailsActivity::class.java)
////            intent.putExtra("id",position)
////            context.startActivity(intent)
//    }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_file , parent , false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}