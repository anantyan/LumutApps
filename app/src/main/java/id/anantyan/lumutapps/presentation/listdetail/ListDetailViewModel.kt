package id.anantyan.lumutapps.presentation.listdetail

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
class ListDetailViewModel(
    private val repository: TodoRepository
) : ViewModel() {
    private var _result: MutableStateFlow<UIState<ResponseItem>> = MutableStateFlow(UIState.Success(ResponseItem()))

    val result: StateFlow<UIState<ResponseItem>> = _result

    fun result(id: Int) {
        viewModelScope.launch {
            repository.result(id).collect {
                _result.value = it
            }
        }
    }
}