package com.example.core.mvvm


interface Interactor<T> {
    //Правило получения данных для экрана пользователя(прослойка)
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}