package com.example.core.mvvm

import androidx.lifecycle.LiveData
import com.example.translateword.data.AppState
import com.example.translateword.data.DataModel
import com.example.translateword.data.Meanings
import com.example.translateword.mvpmainfrag.MainInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val interactor: MainInteractor) :
    BaseViewMode<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {

        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            _mutableLiveData.postValue(parseSearchResults(interactor.getData(word, isOnline)))
        }

    private fun parseSearchResults(data: AppState): AppState? {
        val newSearchResults = arrayListOf<DataModel>()
        when (data) {
            is AppState.Success -> {
                val searchResult = data.data
                if(!searchResult.isNullOrEmpty()) {
                    for (searchResult in searchResult) {
                        parseResult(searchResult, newSearchResults)
                    }
                }
            }
        }
        return AppState.Success(newSearchResults)
    }

    private fun parseResult(dataModel: DataModel, newSearchResults: ArrayList<DataModel>) {
        if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
            val newMeanings = arrayListOf<Meanings>()
            for (meaning in dataModel.meanings) {
                if (meaning.translation != null && !meaning.translation.translation.isNullOrBlank()) {
                    newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
                }
            }
            if (newMeanings.isNotEmpty()) {
                newSearchResults.add(DataModel(dataModel.text, newMeanings))
            }
        }
    }


    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.postValue(AppState.Success(null))
        super.onCleared()
    }
}