package com.example.translateword.mvpmainfrag

import com.example.translateword.AppState
import com.example.translateword.DataModel
import com.example.translateword.Interactor
import com.example.translateword.Repository
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
): Interactor<AppState> {

    override fun getData(
        word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it)}
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}