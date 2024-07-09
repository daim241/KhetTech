package com.example.khettech.ui.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.khettech.ui.journal.viewModel.JournalViewModel
import com.example.khettech.repository.JournalRepository

@Suppress("UNCHECKED_CAST")
class JournalViewModelFactory(private val journalRepository: JournalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            return JournalViewModel(journalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}