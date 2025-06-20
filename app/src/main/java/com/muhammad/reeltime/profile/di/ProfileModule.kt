package com.muhammad.reeltime.profile.di

import com.muhammad.reeltime.profile.data.ProfileRespositoryImp
import com.muhammad.reeltime.profile.domain.ProfileRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.*

val profileModule = module {
    singleOf(::ProfileRespositoryImp).bind<ProfileRepository>()
}