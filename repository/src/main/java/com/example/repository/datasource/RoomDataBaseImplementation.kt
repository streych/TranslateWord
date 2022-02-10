package com.example.repository.datasource

import com.example.model.data.AppState
import com.example.model.data.DataModel
import com.example.translateword.description.convertDataModelSuccessToEntity
import com.example.translateword.description.mapHistoryEntityToSearchResult
import com.example.translateword.room.HistoryDao

class RoomDataBaseImplementation(private val historyDao: HistoryDao): DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }


}
