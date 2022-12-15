package com.aprianto.core.data.source.remote

import android.util.Log
import com.aprianto.core.data.source.remote.network.ApiResponse
import com.aprianto.core.data.source.remote.network.ApiService
import com.aprianto.core.data.source.remote.response.MenuResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null
    }

    suspend fun getAllMenu(): Flow<ApiResponse<List<MenuResponse>>> {

        return flow {
            try {
                val response = apiService.getListMenu()
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}