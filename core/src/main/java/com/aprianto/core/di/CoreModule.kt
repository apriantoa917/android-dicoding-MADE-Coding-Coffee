package com.aprianto.core.di

import com.aprianto.core.BuildConfig
import androidx.room.Room
import com.aprianto.core.data.MenuRepository
import com.aprianto.core.data.source.local.LocalDataSource
import com.aprianto.core.data.source.local.room.MenuDatabase
import com.aprianto.core.data.source.remote.RemoteDataSource
import com.aprianto.core.data.source.remote.network.ApiService
import com.aprianto.core.domain.repository.IMenuRepository
import com.aprianto.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MenuDatabase::class.java, "Menu.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
}

val networkModule = module {
    single {
        val certificatePinner = CertificatePinner.Builder()
            .add(BuildConfig.API_HOST, BuildConfig.API_SSH)
            .build()
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
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