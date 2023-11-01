package com.maxim.getdatafromapiandshow2

import android.app.Application
import com.maxim.getdatafromapiandshow2.data.BaseRepository
import com.maxim.getdatafromapiandshow2.data.net.BaseCloudDataSource
import com.maxim.getdatafromapiandshow2.data.net.BaseFailureHandler
import com.maxim.getdatafromapiandshow2.data.net.FactService
import com.maxim.getdatafromapiandshow2.domain.BaseInteractor
import com.maxim.getdatafromapiandshow2.presentation.BaseCommunication
import com.maxim.getdatafromapiandshow2.presentation.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class App : Application() {
    lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder().baseUrl("https://www.google.com")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val repository = BaseRepository(BaseCloudDataSource(retrofit.create(FactService::class.java)))
        viewModel = MainViewModel(BaseInteractor(repository, BaseFailureHandler()), BaseCommunication())
    }
}