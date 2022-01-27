package com.example.translateword

interface View {
    // Нижний уровень. View знает о контексте и фреймворке
    //Показываем пользователю Перевод или Ошибку, Загрузку
    fun renderData(appState: AppState)
}