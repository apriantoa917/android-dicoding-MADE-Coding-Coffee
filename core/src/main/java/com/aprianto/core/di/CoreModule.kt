package com.aprianto.core.di

import androidx.room.Room
import com.aprianto.core.data.MenuRepository
import com.aprianto.core.data.source.local.LocalDataSource
import com.aprianto.core.data.source.local.room.MenuDatabase
import com.aprianto.core.data.source.remote.RemoteDataSource
import com.aprianto.core.data.source.remote.network.ApiService
import com.aprianto.core.domain.repository.IMenuRepository
import com.aprianto.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {

    factory { get<MenuDatabase>().menuDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MenuDatabase::class.java, "Menu.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://coding-coffee-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMenuRepository> { MenuRepository(get(), get(), get()) }
}