package id.anantyan.lumutapps.data.repository

import android.util.Log
import id.anantyan.lumutapps.common.UIState
import id.anantyan.lumutapps.data.remote.model.ResponseItem
import id.anantyan.lumutapps.data.remote.service.TodoApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Arya Rezza Anantya on 04/06/24.
 */
class TodoRepository(
    private val todoApi: TodoApi
) {
    fun results(): Flow<UIState<List<ResponseItem>>> {
        return flow {
            emit(UIState.Loading())
            val response = todoApi.results()
            if (response.isSuccessful) {
                emit(UIState.Success(response.body() ?: emptyList()))
            } else {
                emit(UIState.Error(null, "Terjadi kesalahan!"))
            }
        }
    }

    fun result(id: Int): Flow<UIState<ResponseItem>> {
        return flow {
            emit(UIState.Loading())
            val response = todoApi.result(id)
            if (response.isSuccessful) {
                emit(UIState.Success(response.body() ?: ResponseItem()))
            } else {
                emit(UIState.Error(null, "Terjadi kesalahan!"))
            }
        }
    }
}