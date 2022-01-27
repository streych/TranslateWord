package com.example.translateword

import io.reactivex.Observable


interface Interactor<T> {
    //Правило получения данных для экрана пользователя(прослойка)
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}