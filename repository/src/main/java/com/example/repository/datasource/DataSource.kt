package com.example.repository.datasource

interface DataSource<T> {
    //Источник данных для репозитория
    suspend fun getData( word: String): T

}