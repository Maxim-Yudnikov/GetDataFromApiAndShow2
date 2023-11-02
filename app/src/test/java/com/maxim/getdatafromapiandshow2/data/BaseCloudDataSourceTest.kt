package com.maxim.getdatafromapiandshow2.data

import com.maxim.getdatafromapiandshow2.data.domain.ServiceUnavailableException
import com.maxim.getdatafromapiandshow2.data.net.BaseCloudDataSource
import com.maxim.getdatafromapiandshow2.data.net.FactService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseCloudDataSourceTest {

    lateinit var mockWebService: MockWebServer
    lateinit var factService: FactService

    @Before
    fun before() {
        mockWebService = MockWebServer()
        factService = Retrofit.Builder().baseUrl(mockWebService.url("/"))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(FactService::class.java)
    }

    @After
    fun after() {
        mockWebService.shutdown()
    }

    @Test
    fun test_get_item_success() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setBody("[{\"fact\": \"Some fact from server\"}]")
        mockWebService.enqueue(mockResponse)
        val cloudDataSource = BaseCloudDataSource(factService)

        val actual = cloudDataSource.getFact()
        val expected = DataItem.BaseDataItem("Some fact from server")
        assertEquals(expected, actual)
    }

    @Test(expected = ServiceUnavailableException::class)
    fun test_get_item_service_unavailable() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebService.enqueue(mockResponse)
        val cloudDataSource = BaseCloudDataSource(factService)

        val actual = cloudDataSource.getFact()
    }
}