package com.aprianto.core.data.source.remote.network

import com.aprianto.core.data.source.remote.response.MenuResponse
import retrofit2.http.GET

interface ApiService {
    @GET("menu.json?print=pretty")
    suspend fun getListMenu():  List<MenuResponse>
}
