package com.example.translateword.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.translateword.AppState
import com.example.translateword.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewMode<T : AppState>(
    protected val liveDataForViewToObserve: MutableLiveData<T> =
        MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable =
        CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {

    open fun getData(word: String, isOnline: Boolean): LiveData<T> =
        liveDataForViewToObserve

    override fun onCleared() {
        compositeDisposable.clear()
    }
}