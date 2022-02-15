package com.example.translateword

import com.example.model.data.AppState

interface View {
    fun renderData(appState: AppState)
}