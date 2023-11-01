package com.maxim.getdatafromapiandshow2.data.net

import com.maxim.getdatafromapiandshow2.data.DataItem
import com.maxim.getdatafromapiandshow2.data.domain.NoConnectionException
import com.maxim.getdatafromapiandshow2.data.domain.ServiceUnavailableException
import java.net.UnknownHostException

class BaseCloudDataSource(private val service: FactService): CloudDataSource {
    override suspend fun getFact(): DataItem {
        return try {
            service.getFact().execute().body()!![0].toDataItem()
        } catch (e: Exception) {
            when(e) {
                is UnknownHostException -> throw NoConnectionException()
                else -> throw ServiceUnavailableException()
            }
        }
    }
}