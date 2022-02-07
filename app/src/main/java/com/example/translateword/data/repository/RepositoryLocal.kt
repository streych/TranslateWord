package com.example.translateword.data.repository

import com.example.translateword.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
}
