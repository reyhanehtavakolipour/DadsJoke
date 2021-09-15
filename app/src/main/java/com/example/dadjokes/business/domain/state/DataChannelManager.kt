package com.example.dadjokes.business.domain.state


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dadjokes.util.printLogD
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
abstract class DataChannelManager<ViewState> {

    private var channelScope: CoroutineScope? = null
    private val stateEventManager: StateEventManager = StateEventManager()


    val shouldDisplayProgressBar = stateEventManager.shouldDisplayProgressBar

    @IgnoredOnParcel
    private val _stateMessage: MutableLiveData<StateMessage?> = MutableLiveData()

    @IgnoredOnParcel
    val stateMessage: LiveData<StateMessage?>
        get() = _stateMessage

    fun setupChannel(){
        cancelJobs()
    }

    abstract fun handleNewData(data: ViewState)


    fun launchJob(
        stateEvent: StateEvent,
        jobFunction: Flow<DataState<ViewState>?>
    ){
            printLogD("DCM", "launching job: ${stateEvent.eventName()}")
            addStateEvent(stateEvent)
            jobFunction
                .onEach { dataState ->
                    dataState?.let { dState ->
                        withContext(Main){
                            dataState.data?.let { data ->
                                handleNewData(data)
                            }
                            dataState.stateMessage?.let { stateMessage ->
                                _stateMessage.value = stateMessage
                            }
                            dataState.stateEvent?.let { stateEvent ->
                                removeStateEvent(stateEvent)
                            }
                        }
                    }
                }
                .launchIn(getChannelScope())
    }



    private fun clearActiveStateEventCounter()
            = stateEventManager.clearActiveStateEventCounter()

    private fun addStateEvent(stateEvent: StateEvent)
            = stateEventManager.addStateEvent(stateEvent)

    private fun removeStateEvent(stateEvent: StateEvent?)
            = stateEventManager.removeStateEvent(stateEvent)

    private fun isStateEventActive(stateEvent: StateEvent)
            = stateEventManager.isStateEventActive(stateEvent)

    fun isJobAlreadyActive(stateEvent: StateEvent): Boolean {
        return isStateEventActive(stateEvent)
    }

    fun clearErrorMessage(){
        _stateMessage.value = null
    }

    private fun getChannelScope(): CoroutineScope {
        return channelScope?: setupNewChannelScope(CoroutineScope(IO))
    }

    private fun setupNewChannelScope(coroutineScope: CoroutineScope): CoroutineScope{
        channelScope = coroutineScope
        return channelScope as CoroutineScope
    }

    private fun cancelJobs(){
        if(channelScope != null){
            if(channelScope?.isActive == true){
                channelScope?.cancel()
            }
            channelScope = null
        }
        clearActiveStateEventCounter()
    }

}
