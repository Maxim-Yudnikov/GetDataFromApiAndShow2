package com.maxim.getdatafromapiandshow2

import android.app.Application
import com.maxim.getdatafromapiandshow2.data.BaseRepository
import com.maxim.getdatafromapiandshow2.data.cache.BaseCacheDataSource
import com.maxim.getdatafromapiandshow2.data.cache.BaseCacheModule
import com.maxim.getdatafromapiandshow2.data.cache.BaseCachedItem
import com.maxim.getdatafromapiandshow2.data.cache.CacheDataSource
import com.maxim.getdatafromapiandshow2.data.cache.MockCacheDataSource
import com.maxim.getdatafromapiandshow2.data.net.BaseCloudDataSource
import com.maxim.getdatafromapiandshow2.data.net.BaseFailureHandler
import com.maxim.getdatafromapiandshow2.data.net.FactService
import com.maxim.getdatafromapiandshow2.data.net.MockCloudDataSource
import com.maxim.getdatafromapiandshow2.domain.BaseInteractor
import com.maxim.getdatafromapiandshow2.presentation.BaseCommunication
import com.maxim.getdatafromapiandshow2.presentation.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    lateinit var viewModel: MainViewModel

    private val useMocks = false

    override fun onCreate() {
        super.onCreate()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()

        val cacheDataSource = if (useMocks) MockCacheDataSource() else BaseCacheDataSource(
            BaseCacheModule(this).provideDataBase().dao()
        )
        val cloudDataSource = if (useMocks) MockCloudDataSource() else BaseCloudDataSource(
            retrofit.create(FactService::class.java)
        )

        val repository = BaseRepository(
            cloudDataSource,
            cacheDataSource,
            BaseCachedItem()
        )
        viewModel =
            MainViewModel(BaseInteractor(repository, BaseFailureHandler()), BaseCommunication())
    }
}