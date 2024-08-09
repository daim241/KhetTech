package com.example.khettech.ui.journal.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.khettech.R
import com.example.khettech.databinding.JournalAdapterBinding
import com.example.khettech.ui.journal.viewModel.JournalViewModel
import com.example.khettech.models.ModelPlantJournal

class JournalAdapter(
    private val context: Context,
    private val journalViewModel: JournalViewModel
) : RecyclerView.Adapter<JournalAdapter.JournalAdapterViewHolder>() {

    private var plants: MutableList<ModelPlantJournal> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalAdapterViewHolder {
        val binding =
            JournalAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JournalAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JournalAdapterViewHolder, position: Int) {
        val plant = plants[position]
        holder.editName.setText(plant.name)
        holder.editDescription.setText(plant.description)
        if (plant.image.isNullOrEmpty()) {
            holder.plantImage.setImageResource(R.drawable.plmaino)
        } else {
            holder.plantImage.setImageURI(Uri.parse(plant.image))
        }
        holder.date.setText(plant.date)

        holder.editName.isEnabled = false
        holder.editDescription.isEnabled = false

        holder.btnUpdate.setOnClickListener {
            if (holder.btnUpdate.text.toString() == "Update") {
                holder.btnUpdate.setText(R.string.save)

                holder.editName.isEnabled = true
                holder.editDescription.isEnabled = true
            } else {
                val newName = holder.editName.text.toString()
                val newDescription = holder.editDescription.text.toString()

                // Update the plant data
                plants[position].name = newName
                plants[position].description = newDescription

                holder.btnUpdate.setText(R.string.update)

                holder.editName.isEnabled = false
                holder.editDescription.isEnabled = false

                journalViewModel.update(plant)

                // Notify RecyclerView of the change
                // notifyItemChanged(position)
                Toast.makeText(context, R.string.update_plant, Toast.LENGTH_SHORT).show()
            }
        }

        holder.btnDelete.setOnClickListener {
            journalViewModel.delete(plant)
            // Remove the plant from the list
            // plants.removeAt(position)

            // Notify RecyclerView of the removal
            // notifyItemRemoved(position)
            Toast.makeText(context, R.string.delete_plant, Toast.LENGTH_SHORT).show()
        }
    }


    fun setPlants(plants: MutableList<ModelPlantJournal>) {
        this.plants = plants
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    inner class JournalAdapterViewHolder(
        private val binding: JournalAdapterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val plantImage = binding.imageViewPlant
        val editName = binding.editTextName
        val editDescription = binding.editTextDescription
        val date = binding.journalEntry
        val btnUpdate = binding.buttonUpdate
        val btnDelete = binding.buttonDelete
    }

}