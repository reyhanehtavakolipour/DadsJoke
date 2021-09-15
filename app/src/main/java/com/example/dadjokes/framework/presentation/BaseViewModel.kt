package com.example.dadjokes.framework.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.dadjokes.business.domain.state.*
import com.example.dadjokes.util.Constants.INVALID_STATE_EVENT
import com.example.dadjokes.util.ErrorHandling
import com.example.dadjokes.util.printLogD
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseViewModel<ViewState> : ViewModel()
{
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    private val dataChannelManager: DataChannelManager<ViewState>
            = object: DataChannelManager<ViewState>(){

        override fun handleNewData(data: ViewState) {
            this@BaseViewModel.handleNewData(data)
        }

    }

    val viewState: LiveData<ViewState>
        get() = _viewState

    val shouldDisplayProgressBar: LiveData<Boolean>
            = dataChannelManager.shouldDisplayProgressBar

    val stateMessage: LiveData<StateMessage?>
        get() = dataChannelManager.stateMessage


    fun setupChannel() = dataChannelManager.setupChannel()

    fun clearErrorMessage() {
        dataChannelManager.clearErrorMessage()
    }

    abstract fun handleNewData(data: ViewState)


    abstract fun setStateEvent(stateEvent: StateEvent)


    fun emitInvalidStateEvent(stateEvent: StateEvent) = flow {
        emit(
            DataState.error<ViewState>(
                response = Response(
                    message = INVALID_STATE_EVENT
                ),
                stateEvent = stateEvent
            )
        )
    }

    fun launchJob(
        stateEvent: StateEvent,
        jobFunction: Flow<DataState<ViewState>?>
    )= dataChannelManager.launchJob(stateEvent, jobFunction)


    fun getCurrentViewStateOrNew(): ViewState{
        return viewState.value ?: initNewViewState()
    }

    fun setViewState(viewState: ViewState){
        _viewState.value = viewState
    }

    abstract fun initNewViewState(): ViewState

}








