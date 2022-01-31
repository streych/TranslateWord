package com.example.translateword.mvvm

import androidx.lifecycle.LiveData
import com.example.translateword.AppState
import com.example.translateword.mvpmainfrag.MainInteractor
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

class MainViewModel(private val interactor: MainInteractor) :
    BaseViewMode<AppState>() {

    private var appState: AppState? = null

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }



    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {

        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(
                    doOnSubscribe()
                )
                .subscribeWith(getObserver())
        )

        return super.getData(word, isOnline)
    }

    private fun doOnSubscribe(): (Disposable) -> Unit =
        { liveDataForViewToObserve.value = AppState.Loading(null) }


    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(state: AppState) {
                appState = state
                liveDataForViewToObserve.value = state
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {

            }

        }
    }
}