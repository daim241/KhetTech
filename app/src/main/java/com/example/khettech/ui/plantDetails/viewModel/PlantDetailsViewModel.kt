package com.example.khettech.ui.plantDetails.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khettech.models.PlantDetailsResponse
import com.example.khettech.network.Resource
import com.example.khettech.network.UIStateResponse
import com.example.khettech.repository.PlantDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailsViewModel @Inject constructor(
    private val plantDetailsRepository: PlantDetailsRepository
) : ViewModel() {

    private var _plantDetailsLiveData = MutableLiveData<UIStateResponse<PlantDetailsResponse>>()
    var plantDetailsLiveData: LiveData<UIStateResponse<PlantDetailsResponse>>? =
        _plantDetailsLiveData

    fun getPlantDetails(id: Int) = viewModelScope.launch {
        _plantDetailsLiveData.postValue(UIStateResponse.Loading)
        when (val response = plantDetailsRepository.getPlantListDetails(id)) {
            is Resource.Success -> {
                _plantDetailsLiveData.postValue(UIStateResponse.Success(response.data))
            }

            is Resource.Failure -> {
                _plantDetailsLiveData.postValue(UIStateResponse.Failure(response.errorMsg.toString()))
            }

            is Resource.FailureUnknown -> {
                _plantDetailsLiveData.postValue(UIStateResponse.Failure(response.unknownError))
            }
        }
    }
}