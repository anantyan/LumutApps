package id.anantyan.lumutapps.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseModel(

	@field:SerializedName("Response")
	val response: List<ResponseItem>? = emptyList()
)

data class ResponseItem(

	@field:SerializedName("id")
	val id: Int? = 0,

	@field:SerializedName("completed")
	val completed: Boolean? = false,

	@field:SerializedName("title")
	val title: String? = "",

	@field:SerializedName("userId")
	val userId: Int? = 0
)
