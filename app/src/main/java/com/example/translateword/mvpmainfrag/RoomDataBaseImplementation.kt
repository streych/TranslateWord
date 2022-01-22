package com.example.translateword.mvpmainfrag

import com.example.translateword.DataModel
import com.example.translateword.DataSource
import io.reactivex.Observable

class RoomDataBaseImplementation: DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("Not yet implemented")
    }

}
