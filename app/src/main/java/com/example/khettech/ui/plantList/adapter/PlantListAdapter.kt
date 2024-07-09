package com.example.khettech.ui.plantList.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.khettech.R
import com.example.khettech.base.RecyclerViewClickListener
import com.example.khettech.databinding.CellPlantListBinding
import com.example.khettech.models.ModelPlantList

class PlantListAdapter(
    private var plantlist: MutableList<ModelPlantList>,
    private var listener: RecyclerViewClickListener?
) : RecyclerView.Adapter<PlantListAdapter.PlantListAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantListAdapter.PlantListAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = CellPlantListBinding.inflate(layoutInflater, parent, false)
        return PlantListAdapterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: PlantListAdapter.PlantListAdapterViewHolder,
        position: Int
    ) {
        holder.bind(plantlist[position], position, listener)
    }

    override fun getItemCount(): Int {
        return plantlist.size
    }

    class PlantListAdapterViewHolder(private var binding: CellPlantListBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private lateinit var listener: RecyclerViewClickListener

        fun bind(plantList: ModelPlantList, position: Int, listener: RecyclerViewClickListener?) {
            if (listener != null) {
                this.listener = listener
            }
            if (plantList.default_image != null) {
                Glide.with(binding.root)
                    .load(Uri.parse(plantList.default_image.thumbnail))
                    .into(binding.imageView)
            } else {
                Glide.with(binding.root)
                    .load(R.drawable.plmaino)
                    .into(binding.imageView)
            }

            binding.commonName.text = plantList.common_name
            binding.scientificName.text = "OtherName: ${plantList.scientific_name}"
            binding.cycle.text = "Cycle: ${plantList.cycle}"
            binding.watering.text = "Watering Required: ${plantList.watering}"
            binding.sunLight.text = "Sunlight Required: ${plantList.sunlight}"


            if (listener != null) {
                if (absoluteAdapterPosition != 0) {
                    binding.cellPlantListContainer.setOnClickListener(this)
                }
            }


        }

        override fun onClick(p0: View) {
            if (absoluteAdapterPosition != 0) {
                listener.onItemViewClick(p0, absoluteAdapterPosition)
            }
        }
    }

    fun addData(newData: List<ModelPlantList>) {
        val oldSize = plantlist.size
        plantlist.addAll(newData)
        notifyItemRangeInserted(oldSize, newData.size)
    }

    fun setPlantList(plants: MutableList<ModelPlantList>) {
        this.plantlist = plants
        notifyDataSetChanged()
    }
}