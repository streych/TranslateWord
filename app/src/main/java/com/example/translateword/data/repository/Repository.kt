package com.example.translateword.data.repository

import io.reactivex.Observable

interface Repository<T> {
    //Получение данных для интерактора
    suspend fun getData(word: String): T
}