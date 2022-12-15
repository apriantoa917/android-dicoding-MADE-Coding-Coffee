package com.aprianto.codingcoffee.di

import com.aprianto.core.domain.usecase.MenuInteractor
import com.aprianto.core.domain.usecase.MenuUseCase
import com.aprianto.core.ui.detail.DetailViewModel
import com.aprianto.core.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MenuUseCase> { MenuInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}