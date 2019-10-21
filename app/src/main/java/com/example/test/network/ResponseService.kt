package com.example.test.network

data class ResponseService(
    var code: Int  = 0,
    var hasError: Boolean = false,
    var valueResponse: String = ""
)