package com.example.translateword

import com.example.model.data.AppState
import com.example.core.mvvm.BaseViewMode
import com.example.core.mvvm.Interactor
import moxy.MvpAppCompatFragment

abstract class BaseFragment<T : AppState, I : Interactor<T>> : MvpAppCompatFragment() {

    abstract val model: BaseViewMode<T>

    abstract fun renderData(appState: T)

}