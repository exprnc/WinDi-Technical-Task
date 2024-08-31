package com.exprnc.winditechnicaltask.core

import android.os.Bundle

abstract class Screen<T>(
    val route: T,
    val requestKey: String? = null,
    val args: Bundle = Bundle.EMPTY
)
