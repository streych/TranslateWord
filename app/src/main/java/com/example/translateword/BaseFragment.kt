package com.example.translateword

import androidx.fragment.app.Fragment
import com.example.model.data.AppState
import com.example.core.mvvm.BaseViewMode
import com.example.core.mvvm.Interactor


abstract class BaseFragment<T : AppState, I : Interactor<T>> : Fragment() {

    abstract val model: BaseViewMode<T>

    abstract fun renderData(appState: T)

}