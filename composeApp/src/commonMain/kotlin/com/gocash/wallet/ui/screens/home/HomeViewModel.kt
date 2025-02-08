package com.gocash.wallet.ui.screens.home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    fun loadInitState(onLogged: () -> Unit, onNewUser: () -> Unit) {
        onNewUser()
    }
}