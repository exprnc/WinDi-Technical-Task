package com.exprnc.winditechnicaltask.presentation.features.auth.registration

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.exprnc.winditechnicaltask.MyApplication
import com.exprnc.winditechnicaltask.core.BaseFragment
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.core.LoadingState
import com.exprnc.winditechnicaltask.core.ViewEvent
import com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode.VerificationCodeScreen
import com.exprnc.winditechnicaltask.presentation.ui.components.loading.LoadingPageBlocker
import com.exprnc.winditechnicaltask.utils.navigateViaScreenRoute
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RegFragment : BaseFragment() {

    private val navController by lazy { findNavController() }

    @Inject
    lateinit var assistedFactory: RegViewModel.Factory
    private val viewModel: RegViewModel by viewModels {
        RegViewModel.provideFactory(
            RegArgs.fetchBundleData(requireArguments()),
            assistedFactory
        )
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication).getAppComponent().inject(this)
        super.onAttach(context)
    }

    @Composable
    override fun Content() {
        val viewState = viewModel.viewState.collectAsState().value
        val loadingState = viewModel.loadingState.collectAsState().value

        when (viewState) {
            is RegState.DefaultState -> RegView(viewState = viewState, onIntent = viewModel::obtainIntent)
        }

        when (loadingState) {
            is LoadingState.Enabled -> LoadingPageBlocker()
            else -> Unit
        }
    }

    override fun onSetupView() {
        super.onSetupView()
        viewModel.viewEvent.setResultListener(this)
        onBackPress {
            viewModel.obtainIntent(Intent.Back)
        }
        observeViewModel()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewModel.obtainIntent(Intent.OnConfigurationChanged)
    }

    private fun observeViewModel() {
        viewModel.viewEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach { event ->
            when (event) {
                is ViewEvent.Navigation -> {
                    viewModel.viewEvent.setResultListener(this)
                    navController.navigateViaScreenRoute(event.screen)
                }

                is ViewEvent.Toast.Id -> {
                    Toast.makeText(context, event.id, Toast.LENGTH_SHORT).show()
                }
                is ViewEvent.Toast.Text -> {
                    Toast.makeText(context, event.text, Toast.LENGTH_SHORT).show()
                }

                is ViewEvent.PopBackStack -> {
                    val fm = parentFragmentManager
                    navController.popBackStack()
                    fm.setFragmentResult(RegScreen.TAG, event.bundle)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}