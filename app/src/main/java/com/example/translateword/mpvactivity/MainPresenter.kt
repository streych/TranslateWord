package com.example.translateword.mpvactivity

import com.example.translateword.cicerone.IScreens
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class MainPresenter(val router: Router, val screen: IScreens): MvpPresenter<MainViewAct>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screen.translate())
    }

}