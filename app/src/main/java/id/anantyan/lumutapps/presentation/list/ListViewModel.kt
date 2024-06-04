package id.anantyan.lumutapps.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.lumutapps.common.UIState
import id.anantyan.lumutapps.data.remote.model.ResponseItem
import id.anantyan.lumutapps.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by Arya Rezza Anantya on 04/06/24.
 */
class ListViewModel(
    private val repository: TodoRepository
) : ViewModel() {
    private var _results: MutableStateFlow<UIState<List<ResponseItem>>> = MutableStateFlow(UIState.Loading())

    val results: StateFlow<UIState<List<ResponseItem>>> = _results

    fun results() {
        viewModelScope.launch {
            repository.results().collect {
                Log.d("DEBUGGING", "${it.data}")
                _results.value = it
            }
        }
    }
}