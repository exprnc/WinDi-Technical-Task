package com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import kotlinx.parcelize.Parcelize

@Parcelize
class VerificationCodeArgs(
    val phone: String,
) : Parcelable {

    companion object {
        private const val ARGS_KEY = "VerificationCodeArgs"
        fun fetchBundleData(bundle: Bundle): VerificationCodeArgs = requireNotNull(bundle.getParcelable(ARGS_KEY))
    }

    fun toBundle() = bundleOf(ARGS_KEY to this)
}