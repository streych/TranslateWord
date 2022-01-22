package com.example.translateword

interface Presenter<T : AppState, V : View> {

    fun attachView(view: V) // присоеденить view
    fun detachView(view: V) // отсоеденить view
    //получаем слово и проверяем откуда брать перевод сеть или база
    fun getData(word: String, isOnline: Boolean)
}