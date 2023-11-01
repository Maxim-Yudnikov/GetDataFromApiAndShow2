package com.maxim.getdatafromapiandshow2

import android.app.Application
import com.maxim.getdatafromapiandshow2.domain.BaseInteractor
import com.maxim.getdatafromapiandshow2.presentation.BaseCommunication
import com.maxim.getdatafromapiandshow2.presentation.MainViewModel

class App: Application() {
    lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        //viewModel = MainViewModel(BaseInteractor(), BaseCommunication())
    }
}