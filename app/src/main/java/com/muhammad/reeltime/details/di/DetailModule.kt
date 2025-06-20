package com.muhammad.reeltime.details.di

import com.muhammad.reeltime.details.data.remote.repository.DetailRepositoryImp
import com.muhammad.reeltime.details.data.remote.repository.SimilarRepositoryImp
import com.muhammad.reeltime.details.data.remote.repository.VideoRepositoryImp
import com.muhammad.reeltime.details.domain.repository.DetailRepository
import com.muhammad.reeltime.details.domain.repository.SimilarRepository
import com.muhammad.reeltime.details.domain.repository.VideoRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val detailModule = module {
    singleOf(::DetailRepositoryImp).bind<DetailRepository>()
    singleOf(::VideoRepositoryImp).bind<VideoRepository>()
    singleOf(::SimilarRepositoryImp).bind<SimilarRepository>()
}