package com.potatomeme.jet_news.presentation.di

import com.potatomeme.jet_news.presentation.ui.home.HomeViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun homeViewModelFactory(): HomeViewModel.HomeViewModelFactory
}