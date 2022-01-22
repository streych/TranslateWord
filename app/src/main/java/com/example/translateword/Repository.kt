package com.example.translateword

import io.reactivex.Observable

interface Repository<T> {
    //Получение данных для интерактора
    fun getData(word: String): Observable<T>
}