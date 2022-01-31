package com.example.translateword

import com.example.translateword.data.AppState

interface View {
    fun renderData(appState: AppState)
}