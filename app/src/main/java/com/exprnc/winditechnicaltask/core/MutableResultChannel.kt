package com.exprnc.winditechnicaltask.core

import android.os.Bundle
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelIterator
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.selects.SelectClause1
import kotlinx.coroutines.selects.SelectClause2

class MutableResultChannel(
    onResult: (String, Bundle) -> Unit = { _, _ -> }
) : ResultFlow(onResult), Channel<ViewEvent> {

    private val _viewEvent = Channel<ViewEvent>(Channel.BUFFERED)

    override suspend fun send(element: ViewEvent) {
        when (element) {
            is ViewEvent.Navigation -> {
                if (!element.screen.requestKey.isNullOrEmpty()) {
                    pendingExecution = PendingExecution(
                        requestKey = element.screen.requestKey
                    )
                }
            }
            is ViewEvent.PopBackStack -> {
                isPopBackStackEvent.set(true)
            }
        }
        _viewEvent.send(element)
    }

    @InternalCoroutinesApi
    suspend fun setResultListener(fragmentResultFlow: SharedFlow<ResultData>) {
        fragmentResultFlow.collect {
            obtainResult(it.resultKey, it.data)
        }
    }

    @ExperimentalCoroutinesApi
    override val isClosedForReceive: Boolean = _viewEvent.isClosedForReceive

    @ExperimentalCoroutinesApi
    override val isEmpty: Boolean = _viewEvent.isEmpty
    override val onReceive: SelectClause1<ViewEvent> = _viewEvent.onReceive
    override val onReceiveCatching: SelectClause1<ChannelResult<ViewEvent>> = _viewEvent.onReceiveCatching

    override fun cancel(cause: CancellationException?) {
        _viewEvent.cancel(cause)
    }

    override fun iterator(): ChannelIterator<ViewEvent> {
        return _viewEvent.iterator()
    }

    override suspend fun receive(): ViewEvent {
        return _viewEvent.receive()
    }

    override suspend fun receiveCatching(): ChannelResult<ViewEvent> {
        return _viewEvent.receiveCatching()
    }

    override fun tryReceive(): ChannelResult<ViewEvent> {
        return _viewEvent.tryReceive()
    }

    @ExperimentalCoroutinesApi
    override val isClosedForSend: Boolean = _viewEvent.isClosedForSend
    override val onSend: SelectClause2<ViewEvent, SendChannel<ViewEvent>> = _viewEvent.onSend

    @ExperimentalCoroutinesApi
    override fun invokeOnClose(handler: (cause: Throwable?) -> Unit) {
        _viewEvent.invokeOnClose(handler)
    }

    override fun trySend(element: ViewEvent): ChannelResult<Unit> {
        return _viewEvent.trySend(element)
    }

    @Deprecated("Since 1.2.0, binary compatibility with versions <= 1.1.x", level = DeprecationLevel.HIDDEN)
    override fun cancel(cause: Throwable?): Boolean {
        return false
    }

    override fun close(cause: Throwable?): Boolean {
        return _viewEvent.close(cause)
    }

    @OptIn(InternalCoroutinesApi::class)
    override suspend fun collect(collector: FlowCollector<ViewEvent>) {
        _viewEvent.receiveAsFlow().collect(collector)
    }

    data class ResultData(
        val resultKey: String,
        val data: Bundle
    )
}
