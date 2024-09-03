package com.exprnc.winditechnicaltask.presentation.features.profile.editprofile

import android.os.Bundle
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Screen
import com.exprnc.winditechnicaltask.domain.model.User
import com.exprnc.winditechnicaltask.utils.getParcel

class EditProfileScreen(
    args: EditProfileArgs
) : Screen<Int>(
    route = R.id.editProfileFragment,
    args = args.toBundle(),
    requestKey = TAG
) {

    companion object {
        const val TAG = "EditProfileScreen"
        private const val RESULT_KEY = "RESULT_KEY"
        private const val DATA_KEY = "DATA_KEY"

        fun getResult(data: Bundle): Result = Result.valueOf(data.getString(RESULT_KEY).orEmpty())
        fun getUser(data: Bundle): User? = data.getParcel(DATA_KEY)
    }

    enum class Result {
        USER_EDITED,
        CANCELED;

        companion object {
            fun createBundleUserEdited(
                user: User
            ) = Bundle().apply {
                putString(RESULT_KEY, USER_EDITED.name)
                putParcelable(DATA_KEY, user)
            }

            fun createBundleCanceled() = Bundle().apply { putString(RESULT_KEY, CANCELED.name) }
        }
    }
}