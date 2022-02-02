package com.example.translateword.data.datasource

import com.example.translateword.data.DataModel
import com.example.translateword.data.datasource.DataSource
import io.reactivex.Observable

class RoomDataBaseImplementation: DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        TODO("Not yet implemented")
    }

}
