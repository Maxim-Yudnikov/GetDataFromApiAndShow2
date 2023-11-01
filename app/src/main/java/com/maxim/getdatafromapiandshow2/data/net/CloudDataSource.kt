package com.maxim.getdatafromapiandshow2.data.net

import com.maxim.getdatafromapiandshow2.data.DataItem

interface CloudDataSource {
    suspend fun getFact(): DataItem
}