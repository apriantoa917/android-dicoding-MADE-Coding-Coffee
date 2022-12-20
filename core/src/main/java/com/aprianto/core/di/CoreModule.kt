package com.aprianto.core.di

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
        val hostname = "coding-coffee-default-rtdb.firebaseio.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/bfVq6ANE2IVTazOGo/DQB0r3l6mHYdkugKqEjlCfT7s=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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