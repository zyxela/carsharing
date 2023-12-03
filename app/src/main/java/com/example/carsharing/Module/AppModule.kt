package com.example.carsharing.Module

import com.example.carsharing.AdminViewModel
import com.example.carsharing.ChooseCarViewModel
import com.example.carsharing.MeetViewModel
import com.example.carsharing.ProfileViewModel
import com.example.carsharing.repository.AdminRepository
import com.example.carsharing.repository.ChooseCarRepository
import com.example.carsharing.repository.MeetRepository
import com.example.carsharing.repository.ProfileRepository
import com.example.carsharing.repositoryImpl.AdminRepositoryImpl
import com.example.carsharing.repositoryImpl.ChooseCarRepositoryImpl
import com.example.carsharing.repositoryImpl.MeetRepositoryImpl
import com.example.carsharing.repositoryImpl.ProfileRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<ChooseCarRepository> { ChooseCarRepositoryImpl() }
    viewModel { ChooseCarViewModel(get()) }

    factory<ProfileRepository> { ProfileRepositoryImpl() }
    viewModel { ProfileViewModel(get()) }

    factory<AdminRepository> { AdminRepositoryImpl() }
    viewModel { AdminViewModel(get()) }

    factory<MeetRepository> { MeetRepositoryImpl() }
    viewModel { MeetViewModel(get()) }
}
