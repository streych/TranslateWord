package com.example.translateword.cicerone

import com.example.translateword.mvpmainfrag.MainFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens : IScreens {
    override fun translate() = FragmentScreen { MainFragment() }
   }