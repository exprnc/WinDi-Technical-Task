package com.exprnc.winditechnicaltask.presentation.features.auth

import android.os.Bundle
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Screen

class AuthScreen : Screen<Int>(
    route = R.id.authFragment,
    requestKey = TAG
) {
    companion object {
        const val TAG = "AuthScreen"
        private const val RESULT_KEY = "RESULT_KEY"
        private const val TAIL_KEY = "TAIL_KEY"

        fun getResult(data: Bundle): Result = Result.valueOf(data.getString(RESULT_KEY).orEmpty())
        fun getTail(data: Bundle): String? = data.getString(TAIL_KEY)
    }

    enum class Result {
        CARD_CREATED,
        CANCELED;

        companion object {
            fun createBundleCardCreated(
                cardTail: String
            ) = Bundle().apply {
                putString(RESULT_KEY, CARD_CREATED.name)
                putString(TAIL_KEY, cardTail)
            }

            fun createBundleCanceled() = Bundle().apply { putString(RESULT_KEY, CANCELED.name) }
        }
    }
}