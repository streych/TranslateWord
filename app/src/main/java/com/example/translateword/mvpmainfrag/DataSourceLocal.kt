package com.example.translateword.mvpmainfrag

import com.example.translateword.DataModel
import com.example.translateword.DataSource
import io.reactivex.Observable

class DataSourceLocal(
    private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}

