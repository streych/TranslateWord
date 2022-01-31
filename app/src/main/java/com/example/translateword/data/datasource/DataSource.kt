package com.example.translateword.data.datasource

import io.reactivex.Observable

interface DataSource<T> {
    //Источник данных для репозитория
    suspend fun getData( word: String): T
}