package com.muhammad.reeltime.categories.di

import com.muhammad.reeltime.categories.data.CategoryRepositoryImp
import com.muhammad.reeltime.categories.domain.CategoryRepository
import com.muhammad.reeltime.categories.presentation.CategoriesViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.*

val categoryModule = module {
    singleOf(::CategoryRepositoryImp).bind<CategoryRepository>()
    viewModelOf(::CategoriesViewModel)
}