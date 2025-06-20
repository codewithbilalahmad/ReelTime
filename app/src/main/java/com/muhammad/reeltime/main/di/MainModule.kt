package com.muhammad.reeltime.main.di

import com.muhammad.reeltime.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    viewModelOf(::MainViewModel)
}