package com.aprianto.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aprianto.core.di.Injection
import com.aprianto.core.domain.usecase.MenuUseCase
import com.aprianto.core.ui.detail.DetailViewModel
import com.aprianto.core.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val menuUseCase: MenuUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideMenuUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(menuUseCase) as T
            }
//            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
//                FavoriteViewModel(tourismUseCase) as T
//            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(menuUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}