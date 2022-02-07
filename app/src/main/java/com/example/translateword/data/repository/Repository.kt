package com.example.translateword.data.repository

interface Repository<T> {

    suspend fun getData(word: String): T
}