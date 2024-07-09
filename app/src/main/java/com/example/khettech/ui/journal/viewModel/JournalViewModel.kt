package com.example.khettech.ui.journal.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khettech.models.ModelPlantJournal
import com.example.khettech.repository.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val journalRepository: JournalRepository
) : ViewModel() {

    val allPlants: LiveData<MutableList<ModelPlantJournal>> = journalRepository.allPlants

    fun insert(plantJournal: ModelPlantJournal) = viewModelScope.launch {
        journalRepository.insert(plantJournal)
    }

    fun update(plantJournal: ModelPlantJournal) = viewModelScope.launch {
        journalRepository.update(plantJournal)
    }

    fun delete(plantJournal: ModelPlantJournal) = viewModelScope.launch {
        journalRepository.delete(plantJournal)
    }

}