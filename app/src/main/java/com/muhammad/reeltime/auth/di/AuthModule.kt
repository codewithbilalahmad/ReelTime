package com.muhammad.reeltime.auth.di

import android.content.Context
import com.muhammad.reeltime.auth.data.remote.repository.AuthRespositoryImp
import com.muhammad.reeltime.auth.domain.repository.AuthRepository
import com.muhammad.reeltime.auth.domain.usecase.UserDataValidator
import com.muhammad.reeltime.auth.presentation.login.LoginViewModel
import com.muhammad.reeltime.auth.presentation.register.RegisterViewModel
import com.muhammad.reeltime.main.ReelTimeApplication
import com.muhammad.reeltime.utils.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    single { ReelTimeApplication.INSTANCE }
    single { HttpClientFactory.build() }
    single {
        get<ReelTimeApplication>().applicationContext.getSharedPreferences(
            "user_info", Context.MODE_PRIVATE
        )
    }
    singleOf(::AuthRespositoryImp).bind<AuthRepository>()
    single {
        UserDataValidator()
    }
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}