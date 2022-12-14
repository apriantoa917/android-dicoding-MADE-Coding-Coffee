package com.aprianto.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aprianto.core.data.source.remote.network.ApiResponse
import com.aprianto.core.data.source.remote.network.ApiService
import com.aprianto.core.data.source.remote.response.MenuResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllMenu(): LiveData<ApiResponse<List<MenuResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<MenuResponse>>>()

        //get data from remote api
        val client = apiService.getListMenu()

        client.enqueue(object : Callback<List<MenuResponse>> {
            override fun onResponse(
                call: Call<List<MenuResponse>>,
                response: Response<List<MenuResponse>>
            ) {
                val dataArray = response.body()
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
                /* TODO -> belum di casting hasil */
            }

            override fun onFailure(call: Call<List<MenuResponse>>, t: Throwable) {
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }
}