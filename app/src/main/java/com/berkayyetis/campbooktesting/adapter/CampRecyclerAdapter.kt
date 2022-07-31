package com.berkayyetis.campbooktesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.berkayyetis.campbooktesting.R
import com.berkayyetis.campbooktesting.roomdb.Camp
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class CampRecyclerAdapter @Inject constructor(
    var glide : RequestManager) : RecyclerView.Adapter<CampRecyclerAdapter.CampViewHolder>() {
    class CampViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<Camp>(){ //güncelleme oldugunda değişip değişmediğini anlamak için
        override fun areItemsTheSame(oldItem: Camp, newItem: Camp): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Camp, newItem: Camp): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var camps: List<Camp>
    get() = recyclerListDiffer.currentList
    set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.camp_row,parent,false)
        return CampViewHolder(view)
    }

    override fun onBindViewHolder(holder: CampViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.campRowImageView)
        val nameText = holder.itemView.findViewById<TextView>(R.id.campNameTextView)
        val artistNameText = holder.itemView.findViewById<TextView>(R.id.artistNameTextView)
        val yearTextView = holder.itemView.findViewById<TextView>(R.id.yearTextView)
        val camp = camps[position]
        holder.itemView.apply {
            nameText.text = "Camp name:  ${camp.name}"
            artistNameText.text = "Camp city:  ${camp.campCity}"
            yearTextView.text = "Year:  ${camp.year}"
            glide.load(camp.imageUrl).into(imageView)
        }
    }

    override fun getItemCount(): Int {
       return camps.size
    }
}