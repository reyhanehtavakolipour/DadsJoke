package com.example.dadjokes.util

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

object ErrorHandling {

    val context = BaseApplication.applicationContext()


    fun networkError() = "network error"


    fun unknownError() = "unknown error"

    fun getErrorApiMessage(apiResponse: retrofit2.Response<*>?): String
    {
        var errorMsg = "try again 1"
        var errorBodyJson = JSONObject()
        try {
            val body = apiResponse?.errorBody()?.string()
            Log.e("ERROR_BODY_RESPONSE" , body.toString())
            if (body != null)
                errorBodyJson = JSONObject(body)
            val msg = errorBodyJson.optString("message")
            Log.e("ERROR_API" , apiResponse?.code().toString()+" "+ msg.toString())

            when(apiResponse?.code())
            {
                400 ->  errorMsg = "try again 2"
                401 ->  errorMsg ="try again 3"
                403 ->  errorMsg = "try again 4"
                404 -> errorMsg = "try again 5"
                500 ->  errorMsg = "try again 6"

            }
        }
        catch (e: JSONException)
        {
            Log.e("ERROR_PARSE_CATCH" , e.localizedMessage?.toString().toString())
        }

        return errorMsg
    }
}