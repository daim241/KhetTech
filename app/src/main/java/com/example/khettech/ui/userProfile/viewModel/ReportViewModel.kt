package com.example.khettech.ui.userProfile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khettech.models.ModelReport
import com.example.khettech.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {

    fun insert(report: String) {
        val description = ModelReport(report_description = report)
        viewModelScope.launch {
            reportRepository.insert(description)
        }
    }
}