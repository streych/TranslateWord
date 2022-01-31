package com.example.translateword.mvpmainfrag

import com.example.translateword.data.DataModel
import com.example.translateword.data.datasource.DataSource
import com.example.translateword.data.datasource.RetrofitImplementation
import io.reactivex.Observable

class DataSourceRemote (
    private val remoteProvider: RetrofitImplementation = RetrofitImplementation()
)
    : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)

}