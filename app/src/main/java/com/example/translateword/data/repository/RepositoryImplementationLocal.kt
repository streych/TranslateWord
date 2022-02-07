package com.example.translateword.data.repository

import com.example.translateword.data.AppState
import com.example.translateword.data.DataModel
import com.example.translateword.data.datasource.DataSourceLocal
import com.example.translateword.data.datasource.RoomDataBaseImplementation

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<DataModel>>) :
    RepositoryLocal<List<DataModel>> {


    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}
