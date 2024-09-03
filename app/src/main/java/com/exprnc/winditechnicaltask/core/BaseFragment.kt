package com.exprnc.winditechnicaltask.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.exprnc.winditechnicaltask.databinding.FragmentComposeLayoutBinding

abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onSetupView()
        val binding = FragmentComposeLayoutBinding.inflate(inflater, container, false)
        binding.root.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner))
        binding.root.setContent { Content() }
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        retainInstance = true
    }

    open fun onSetupView() {}

    fun onBackPress(callback: OnBackPressedCallback.() -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(owner = this, enabled = true) {
            callback.invoke(this)
        }
    }

    @Composable
    abstract fun Content()
}