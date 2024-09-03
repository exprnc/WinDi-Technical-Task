package com.exprnc.winditechnicaltask.presentation.features.profile.editprofile

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
class EditProfileArgs(
    val name: String,
    val city: String,
    val birthday: Date,
    val fileName: String,
    val base64: String
) : Parcelable {

    companion object {
        private const val ARGS_KEY = "EditProfileArgs"
        fun fetchBundleData(bundle: Bundle): EditProfileArgs = requireNotNull(bundle.getParcelable(ARGS_KEY))
    }

    fun toBundle() = bundleOf(ARGS_KEY to this)
}