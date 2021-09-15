package com.example.dadjokes.business.domain.state


data class DataState<T>(
    var stateMessage: StateMessage? = null,
    var data: T? = null,
    var stateEvent: StateEvent? = null
) {

    companion object {

        fun <T> error(
            response: Response,
            stateEvent: StateEvent?
        ): DataState<T> {
            return DataState(
                stateMessage = StateMessage(
                    response
                ),
                stateEvent = stateEvent
            )
        }

        fun <T> data(
            data: T? = null,
            stateEvent: StateEvent?
        ): DataState<T> {
            return DataState(
                data = data,
                stateEvent = stateEvent
            )
        }
    }
}