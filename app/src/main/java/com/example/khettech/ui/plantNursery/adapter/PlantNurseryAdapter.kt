package com.example.khettech.ui.plantNursery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.khettech.R
import com.example.khettech.models.ModelPlantNursery

class PlantNurseryAdapter (private val modelNurseryList: List<ModelPlantNursery>) :
    RecyclerView.Adapter<PlantNurseryAdapter.ModelNurseryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelNurseryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_model_nursery, parent, false)
        return ModelNurseryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ModelNurseryViewHolder, position: Int) {
        val currentItem = modelNurseryList[position]
        holder.nameTextView.text = currentItem.name
        holder.addressTextView.text = currentItem.address
        holder.phoneTextView.text = currentItem.phone
    }

    override fun getItemCount() = modelNurseryList.size

    class ModelNurseryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)
    }
}