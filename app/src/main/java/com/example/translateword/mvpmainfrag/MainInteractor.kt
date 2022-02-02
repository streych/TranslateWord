package com.example.translateword.mvpmainfrag

import com.example.translateword.Interactor
import com.example.translateword.data.AppState
import com.example.translateword.data.DataModel
import com.example.translateword.data.repository.Repository

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
): Interactor<AppState> {

    override suspend fun getData(
        word: String, fromRemoteSource: Boolean
    ): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                remoteRepository
            } else {
                localRepository
            }.getData(word)
        )
    }
}