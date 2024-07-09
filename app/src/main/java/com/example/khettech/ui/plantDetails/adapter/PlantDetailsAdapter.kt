package com.example.khettech.ui.plantDetails.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.khettech.R
import com.example.khettech.databinding.CellPlantDetailsBinding
import com.example.khettech.models.PlantDetailsResponse


class PlantDetailsAdapter(
    private var plantDetailsList: MutableList<PlantDetailsResponse>
) : RecyclerView.Adapter<PlantDetailsAdapter.PlantDetailsAdapterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantDetailsAdapter.PlantDetailsAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = CellPlantDetailsBinding.inflate(layoutInflater, parent, false)
        return PlantDetailsAdapterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: PlantDetailsAdapter.PlantDetailsAdapterViewHolder,
        position: Int
    ) {
        holder.bind(plantDetailsList[position], position)
    }

    override fun getItemCount(): Int {
        return plantDetailsList.size
    }

    class PlantDetailsAdapterViewHolder(private var binding: CellPlantDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plantList: PlantDetailsResponse, position: Int) {

            if (plantList.default_image != null) {
                Glide.with(binding.root)
                    .load(Uri.parse(plantList.default_image.medium_url))
                    .into(binding.detailsImageView)
            } else {
                Glide.with(binding.root)
                    .load(R.drawable.plmaino)
                    .into(binding.detailsImageView)
            }

            binding.commonName.text = plantList.common_name
            binding.origin.text = "Origin: ${plantList.origin}"
            binding.type.text = "Type: ${plantList.type}"
            binding.dimension.text = "Dimensions: ${plantList.dimension}"
            binding.propagation.text = "Propogation: ${plantList.propagation}"
            binding.value.text =
                "Water Required: ${plantList.watering_general_benchmark.value} ${plantList.watering_general_benchmark.unit}"
            binding.pruningMonth.text = "Purning Month: ${plantList.pruning_month}"
            binding.edibleLeaf.text = "Edible Leaf: ${plantList.edible_leaf}"
            binding.cuisine.text = "Cuisine: ${plantList.cuisine}"
            binding.medicinal.text = "Medicinal: ${plantList.medicinal}"
            binding.detailsDescription.text = "Description: ${plantList.description}"
        }
    }

    fun addData(newData: PlantDetailsResponse) {
        plantDetailsList.addAll(listOf(newData))
        notifyDataSetChanged()
    }

}