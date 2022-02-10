package com.example.translateword.koin

import androidx.room.Room
import com.example.model.data.DataModel
import com.example.repository.datasource.RetrofitImplementation
import com.example.repository.datasource.RoomDataBaseImplementation
import com.example.repository.repository.Repository
import com.example.repository.repository.RepositoryImplementation
import com.example.repository.repository.RepositoryImplementationLocal
import com.example.repository.repository.RepositoryLocal
import com.example.translateword.history.HistoryInteractor
import com.example.translateword.history.HistoryViewModel
import com.example.translateword.mvpmainfrag.MainInteractor
import com.example.core.mvvm.MainViewModel
import com.example.translateword.room.HistoryDataBase
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