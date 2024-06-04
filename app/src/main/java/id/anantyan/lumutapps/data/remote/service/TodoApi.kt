package id.anantyan.lumutapps.data.remote.service

import id.anantyan.lumutapps.data.remote.model.ResponseItem
import id.anantyan.lumutapps.data.remote.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Arya Rezza Anantya on 04/06/24.
 */
interface TodoApi {
    @GET("todos")
    suspend fun results(): Response<List<ResponseItem>>

    @GET("todos/{id}")
    suspend fun result(
        @Path("id") id: Int? = 0
    ): Response<ResponseItem>
}