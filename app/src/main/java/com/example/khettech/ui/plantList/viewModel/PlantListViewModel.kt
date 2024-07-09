package com.example.khettech.ui.plantList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khettech.models.PlantResponse
import com.example.khettech.network.Resource
import com.example.khettech.network.UIStateResponse
import com.example.khettech.repository.PlantListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantListViewModel @Inject constructor(
    private val plantListRepository: PlantListRepository
) : ViewModel() {

    private var _plantListLiveData = MutableLiveData<UIStateResponse<PlantResponse>>()
    var plantListLiveData: LiveData<UIStateResponse<PlantResponse>>? = _plantListLiveData


    fun getPlantList(page: Int) = viewModelScope.launch {
        _plantListLiveData.postValue(UIStateResponse.Loading)
        when (val response = plantListRepository.getPlantList(page)) {
            is Resource.Success -> {
                _plantListLiveData.postValue(UIStateResponse.Success(response.data))
            }

            is Resource.Failure -> {
                _plantListLiveData.postValue(UIStateResponse.Failure(response.errorMsg.toString()))
            }

            is Resource.FailureUnknown -> {
                _plantListLiveData.postValue(UIStateResponse.Failure(response.unknownError))
            }
        }
    }

}