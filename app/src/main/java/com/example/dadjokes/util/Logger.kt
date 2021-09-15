package com.example.dadjokes.util

import android.util.Log
import com.example.dadjokes.util.Constants.DEBUG
import com.example.dadjokes.util.Constants.TAG


var isUnitTest = false

fun printLogD(className: String?, message: String ) {
    if (DEBUG && !isUnitTest) {
        Log.e(TAG, "$className: $message")
    }
    else if(DEBUG && isUnitTest){
        println("$className: $message")
    }
}

