package com.maxim.getdatafromapiandshow2.data.net

import com.maxim.getdatafromapiandshow2.data.DataItem

class MockCloudDataSource: CloudDataSource {
    private var count = 0
    override suspend fun getFact(): DataItem {
        count++
        return DataItem.BaseDataItem("item$count")
    }

}