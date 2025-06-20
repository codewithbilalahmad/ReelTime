package com.muhammad.reeltime.search.di

import com.muhammad.reeltime.search.data.repository.SearchRepositoryImp
import com.muhammad.reeltime.search.domain.SearchRespository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val searchModule = module {
    singleOf(::SearchRepositoryImp).bind<SearchRespository>()
}