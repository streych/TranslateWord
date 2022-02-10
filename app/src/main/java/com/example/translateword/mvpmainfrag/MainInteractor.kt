package com.example.translateword.mvpmainfrag

import com.example.core.mvvm.Interactor
import com.example.model.data.AppState
import com.example.model.data.DataModel
import com.example.repository.repository.Repository
import com.example.repository.repository.RepositoryLocal

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
): Interactor<AppState> {

    override suspend fun getData(
        word: String, fromRemoteSource: Boolean
    ): AppState {
        val appState: AppState

        if (fromRemoteSource) {
            appState = AppState.Success(remoteRepository.getData(word))
            localRepository.saveToDB(appState)
        } else {
            appState = AppState.Success(remoteRepository.getData(word))
        }
        return appState
    }
}