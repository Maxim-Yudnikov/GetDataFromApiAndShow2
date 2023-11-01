package com.maxim.getdatafromapiandshow2

import android.app.Application
import com.maxim.getdatafromapiandshow2.data.BaseRepository
import com.maxim.getdatafromapiandshow2.data.cache.BaseCacheDataSource
import com.maxim.getdatafromapiandshow2.data.cache.BaseCacheModule
import com.maxim.getdatafromapiandshow2.data.cache.BaseDataToRoomMapper
import com.maxim.getdatafromapiandshow2.data.cache.CacheModule
import com.maxim.getdatafromapiandshow2.data.cache.RoomFactsDatabase
import com.maxim.getdatafromapiandshow2.data.net.BaseCloudDataSource
import com.maxim.getdatafromapiandshow2.data.net.BaseFailureHandler
import com.maxim.getdatafromapiandshow2.data.net.FactService
import com.maxim.getdatafromapiandshow2.domain.BaseInteractor
import com.maxim.getdatafromapiandshow2.presentation.BaseCommunication
import com.maxim.getdatafromapiandshow2.presentation.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder().baseUrl("https://www.google.com")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val repository = BaseRepository(
            BaseCloudDataSource(retrofit.create(FactService::class.java)),
            BaseCacheDataSource(BaseCacheModule(this).provideDataBase().dao(), BaseDataToRoomMapper())
        )
        viewModel =
            MainViewModel(BaseInteractor(repository, BaseFailureHandler()), BaseCommunication())
    }
}