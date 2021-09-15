package com.example.dadjokes.util

import retrofit2.Response

data class GenericApiResponse<ResponseObject>(
    var isSuccessFull: Boolean ,
    var apiResponse: Response<ResponseObject>?
)
