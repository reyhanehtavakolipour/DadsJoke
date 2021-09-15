package com.example.dadjokes.util

import com.example.dadjokes.business.domain.state.*
import com.example.dadjokes.util.Constants.CACHE_DATA_NULL


abstract class CacheResponseHandler <ViewState, Data>(
    private val response: CacheResult<Data?>,
    private val stateEvent: StateEvent?
){
    suspend fun getResult(): DataState<ViewState>? {

        return when(response){

            is CacheResult.GenericError -> {
                DataState.error(
                    response = Response(
                        message = "${stateEvent?.errorInfo()}\n\nReason: ${response.errorMessage}"
                    ),
                    stateEvent = stateEvent
                )
            }

            is CacheResult.Success -> {
                if(response.value == null){
                    DataState.error(
                        response = Response(
                            message = "${stateEvent?.errorInfo()}\n\nReason: ${CACHE_DATA_NULL}."
                        ),
                        stateEvent = stateEvent
                    )
                }
                else{
                    handleSuccess(resultObj = response.value)
                }
            }

        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): DataState<ViewState>?

}