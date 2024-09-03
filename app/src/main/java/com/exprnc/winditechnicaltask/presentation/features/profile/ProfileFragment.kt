package com.exprnc.winditechnicaltask.presentation.features.profile

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
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.BaseFragment
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.core.LoadingState
import com.exprnc.winditechnicaltask.core.ViewEvent
import com.exprnc.winditechnicaltask.presentation.ui.components.loading.LoadingPageBlocker
import com.exprnc.winditechnicaltask.utils.EXIT_SECOND_TAP_TIME
import com.exprnc.winditechnicaltask.utils.navigateViaScreenRoute
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.system.exitProcess

class ProfileFragment : BaseFragment() {
    private val navController by lazy { findNavController() }
    private var backPressed = 0L

    @Inject
    lateinit var assistedFactory: ProfileViewModel.Factory
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModel.provideFactory(
            assistedFactory
        )
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication).getAppComponent().inject(this)
        super.onAttach(context)
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
                    if (backPressed + EXIT_SECOND_TAP_TIME > System.currentTimeMillis()) {
                        exitProcess(0)
                    } else {
                        Toast.makeText(context, R.string.click_again, Toast.LENGTH_SHORT).show()
                        backPressed = System.currentTimeMillis()
                    }
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    @Composable
    override fun Content() {
        val viewState = viewModel.viewState.collectAsState().value
        val loadingState = viewModel.loadingState.collectAsState().value

        when (viewState) {
            is ProfileState.DefaultState -> ProfileView(navController, viewState, viewModel::obtainIntent)
        }

        when (loadingState) {
            is LoadingState.Enabled -> LoadingPageBlocker()
            else -> Unit
        }
    }
}