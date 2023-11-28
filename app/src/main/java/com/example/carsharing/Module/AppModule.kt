package com.example.carsharing.Module

import com.example.carsharing.ChooseCarViewModel
import com.example.carsharing.ProfileViewModel
import com.example.carsharing.repository.ChooseCarRepository
import com.example.carsharing.repository.ProfileRepository
import com.example.carsharing.repositoryImpl.ChooseCarRepositoryImpl
import com.example.carsharing.repositoryImpl.ProfileRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<ChooseCarRepository> { ChooseCarRepositoryImpl() }
    viewModel { ChooseCarViewModel(get()) }

    factory<ProfileRepository> { ProfileRepositoryImpl() }
    viewModel { ProfileViewModel(get()) }
}
