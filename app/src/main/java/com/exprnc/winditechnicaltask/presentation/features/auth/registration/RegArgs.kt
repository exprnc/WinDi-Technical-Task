package com.exprnc.winditechnicaltask.presentation.features.auth.registration

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import kotlinx.parcelize.Parcelize

@Parcelize
class RegArgs(
    val phone: String,
) : Parcelable {

    companion object {
        private const val ARGS_KEY = "RegArgs"
        fun fetchBundleData(bundle: Bundle): RegArgs = requireNotNull(bundle.getParcelable(ARGS_KEY))
    }

    fun toBundle() = bundleOf(ARGS_KEY to this)
}