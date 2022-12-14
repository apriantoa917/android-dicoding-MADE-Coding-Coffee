package com.aprianto.core.ui.home

import androidx.lifecycle.ViewModel
import com.aprianto.core.domain.usecase.MenuUseCase

class HomeViewModel(menuUseCase: MenuUseCase): ViewModel() {
    val menu = menuUseCase.getAllMenu()
}