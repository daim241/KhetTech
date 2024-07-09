package com.example.khettech.ui.plantList

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.khettech.base.BaseActivity
import com.example.khettech.base.RecyclerViewClickListener
import com.example.khettech.databinding.ActivityPlantListBinding
import com.example.khettech.network.UIStateResponse
import com.example.khettech.ui.plantDetails.PlantDetailsActivity
import com.example.khettech.ui.plantList.adapter.PlantListAdapter
import com.example.khettech.ui.plantList.viewModel.PlantListViewModel
import com.example.khettech.ui.style.PlantListItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantList : BaseActivity(), RecyclerViewClickListener {

    private lateinit var binding: ActivityPlantListBinding

    private val viewModel by viewModels<PlantListViewModel>()

    private var currentPage = 0
    private var isLoading = false
    private lateinit var plantListAdapter: PlantListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityPlantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.plantListRecView.apply {
            plantListAdapter = PlantListAdapter(ArrayList(), this@PlantList)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = plantListAdapter

            val spaceBetweenCells = 16
            addItemDecoration(PlantListItemDecoration(spaceBetweenCells))


            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!isLoading && visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        loadMoreData()
                    }
                }
            })
        }
        loadMoreData()
    }

    private fun loadMoreData() {
        isLoading = true
        currentPage++

        //Call API to fetch the next page
        viewModel.getPlantList(currentPage)

        //Observe API call response
        viewModel.plantListLiveData?.observe(this, Observer {
            when (it) {
                UIStateResponse.Loading -> {
                    showProgressBar()
                }

                is UIStateResponse.Success -> {
                    plantListAdapter.addData(it.data.plantList)
                    isLoading = false
                    dismissProgressBar()
                }

                is UIStateResponse.Failure -> {
                    Toast.makeText(this, it.errorMsg, Toast.LENGTH_SHORT).show()
                    dismissProgressBar()
                }
            }
        })
    }

    override fun onItemViewClick(view: View, position: Int) {

        val intent = Intent(this@PlantList, PlantDetailsActivity::class.java)
        intent.putExtra("plantDetails", position + 1)

        startActivity(intent)
        // Handle item click
//        Toast.makeText(this, "Cell ${position + 1} Clicked", Toast.LENGTH_SHORT).show()
    }
}
