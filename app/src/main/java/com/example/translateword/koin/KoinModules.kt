package com.example.translateword.koin

import androidx.room.Room
import com.example.translateword.data.DataModel
import com.example.translateword.data.datasource.RetrofitImplementation
import com.example.translateword.data.datasource.RoomDataBaseImplementation
import com.example.translateword.data.repository.Repository
import com.example.translateword.data.repository.RepositoryImplementation
import com.example.translateword.data.repository.RepositoryImplementationLocal
import com.example.translateword.data.repository.RepositoryLocal
import com.example.translateword.history.HistoryInteractor
import com.example.translateword.history.HistoryViewModel
import com.example.translateword.mvpmainfrag.MainInteractor
import com.example.translateword.mvvm.MainViewModel
import com.example.translateword.room.HistoryDataBase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<DataModel>>> { RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}