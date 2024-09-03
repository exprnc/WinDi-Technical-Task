package com.exprnc.winditechnicaltask.core

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment

class BaseNavHostFragment : NavHostFragment() {

    override fun onDestroyView() {
        try {
            super.onDestroyView()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    companion object {
        @JvmOverloads
        @JvmStatic
        fun create(
            @NavigationRes graphResId: Int,
            startDestinationArgs: Bundle? = null
        ): BaseNavHostFragment {
            var b: Bundle? = null
            if (graphResId != 0) {
                b = Bundle()
                b.putInt("android-support-nav:fragment:graphId", graphResId)
            }
            if (startDestinationArgs != null) {
                if (b == null) {
                    b = Bundle()
                }
                b.putBundle("android-support-nav:fragment:startDestinationArgs", startDestinationArgs)
            }
            val result = BaseNavHostFragment()
            if (b != null) {
                result.arguments = b
            }
            return result
        }
    }
}