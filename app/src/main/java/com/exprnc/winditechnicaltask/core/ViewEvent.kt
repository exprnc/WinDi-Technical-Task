package com.exprnc.winditechnicaltask.core

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.StringRes

interface ViewEvent {
    class Navigation(val screen: Screen<*>) : ViewEvent
    sealed class Toast : ViewEvent {
        class Id(@StringRes val id: Int) : Toast()
        class Text(val text: String) : Toast()
    }
    class PopBackStack(
        val bundle: Bundle = Bundle.EMPTY,
        @IdRes val id: Int? = null
    ) : ViewEvent
}