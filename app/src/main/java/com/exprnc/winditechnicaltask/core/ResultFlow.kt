package com.exprnc.winditechnicaltask.core

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.atomic.AtomicBoolean

abstract class ResultFlow(
    private val onResult: (String, Bundle) -> Unit
) : Flow<ViewEvent> {

    val isPendingExecution: Boolean
        get() = pendingExecution != null || isPopBackStackEvent.get()
    val currentRequestKey
        get() = pendingExecution?.requestKey

    protected var pendingExecution: PendingExecution? = null
    protected val isPopBackStackEvent = AtomicBoolean(false)

    fun setResultListener(fragment: Fragment) {
        val requestKey = pendingExecution?.requestKey ?: return
        fragment.parentFragmentManager.setFragmentResultListener(requestKey, fragment) { resultKey, data ->
            obtainResult(resultKey, data)
        }
    }

    protected fun obtainResult(resultKey: String, data: Bundle) {
        if (resultKey == pendingExecution?.requestKey) {
            /**
             * В момент вызова [onResult], переменная [pendingExecution] должна быть null
             * для избежания проблем с навигационными ивентами.
             */
            val tmp = pendingExecution
            pendingExecution = null
            onResult(resultKey, data)
            tmp?.onDismiss?.invoke()
        }
    }

    protected class PendingExecution(
        val requestKey: String? = null,
        val onDismiss: (() -> Unit)? = null
    )
}
