package com.example.translateword.data.datasource

import com.example.translateword.data.AppState


interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
}
