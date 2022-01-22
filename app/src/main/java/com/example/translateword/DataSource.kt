package com.example.translateword

import io.reactivex.Observable

interface DataSource<T> {
    //Источник данных для репозитория
    fun getData( word: String): Observable<T>
}