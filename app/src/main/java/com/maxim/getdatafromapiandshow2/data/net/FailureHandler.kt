package com.maxim.getdatafromapiandshow2.data.net

import com.maxim.getdatafromapiandshow2.data.domain.BaseFailure

interface FailureHandler {
    fun handle(e: Exception): BaseFailure
}