package com.example.translateword

import com.example.translateword.data.AppState
import com.example.translateword.mvvm.BaseViewMode
import moxy.MvpAppCompatFragment

abstract class BaseFragment<T : AppState, I : Interactor<T>> : MvpAppCompatFragment() {

    abstract val model: BaseViewMode<T>

    abstract fun renderData(appState: T)

}