package com.example.translateword.mvpmainfrag

import com.example.model.data.DataModel
import com.example.repository.datasource.DataSource
import com.example.repository.datasource.RetrofitImplementation

class DataSourceRemote (
    private val remoteProvider: RetrofitImplementation = RetrofitImplementation()
)
    : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)

}