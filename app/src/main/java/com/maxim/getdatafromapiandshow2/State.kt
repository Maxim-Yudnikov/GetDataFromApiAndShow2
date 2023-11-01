package com.maxim.getdatafromapiandshow2

interface State {
    data class Success(private val text: String): State
    data class Failed(private val text: String): State
}