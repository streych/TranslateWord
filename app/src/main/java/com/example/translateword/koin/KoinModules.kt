package com.example.translateword.koin

import com.example.translateword.data.DataModel
import com.example.translateword.data.repository.Repository
import com.example.translateword.data.repository.RepositoryImplementation
import com.example.translateword.mvpmainfrag.MainInteractor
import com.example.translateword.data.datasource.RetrofitImplementation
import com.example.translateword.data.datasource.RoomDataBaseImplementation
import com.example.translateword.mvvm.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(RoomDataBaseImplementation())
    }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}