package id.anantyan.lumutapps.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.anantyan.lumutapps.data.repository.TodoRepository
import id.anantyan.lumutapps.presentation.listdetail.ListDetailViewModel

class ListDetailFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListDetailViewModel::class.java)) {
            return ListDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}