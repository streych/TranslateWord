package com.example.translateword.mvpmainfrag

import com.example.translateword.Interactor
import com.example.translateword.cicerone.App
import com.example.translateword.data.AppState
import com.example.translateword.data.DataModel
import com.example.translateword.data.repository.Repository
import com.example.translateword.data.repository.RepositoryLocal

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