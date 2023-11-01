package com.maxim.getdatafromapiandshow2.data.net

import com.maxim.getdatafromapiandshow2.data.domain.BaseFailure
import com.maxim.getdatafromapiandshow2.data.domain.NoConnectionError
import com.maxim.getdatafromapiandshow2.data.domain.NoConnectionException
import com.maxim.getdatafromapiandshow2.data.domain.ServiceUnavailableError
import com.maxim.getdatafromapiandshow2.data.domain.ServiceUnavailableException
import com.maxim.getdatafromapiandshow2.data.domain.UnknownError

class BaseFailureHandler: FailureHandler {
    override fun handle(e: Exception): BaseFailure {
        return when(e) {
            is NoConnectionException -> NoConnectionError()
            is ServiceUnavailableException -> ServiceUnavailableError()
            else -> UnknownError()
        }
    }
}