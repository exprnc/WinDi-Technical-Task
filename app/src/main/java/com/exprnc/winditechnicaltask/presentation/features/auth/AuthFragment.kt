package com.exprnc.winditechnicaltask.presentation.features.auth

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arpitkatiyarprojects.countrypicker.CountryPickerOutlinedTextField
import com.exprnc.winditechnicaltask.MyApplication
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.BaseFragment
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.core.LoadingState
import com.exprnc.winditechnicaltask.core.ViewEvent
import com.exprnc.winditechnicaltask.presentation.ui.components.LoadingPageBlocker
import com.exprnc.winditechnicaltask.utils.navigateViaScreenRoute
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthFragment : BaseFragment() {

    private val navController by lazy { findNavController() }

    @Inject
    lateinit var assistedFactory: AuthViewModel.Factory
    private val viewModel: AuthViewModel by viewModels {
        AuthViewModel.provideFactory(
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
                    val fm = parentFragmentManager
                    navController.popBackStack()
                    fm.setFragmentResult(AuthScreen.TAG, event.bundle) //Указываешь ключ текущего экрана
                }

                is AuthEvent.OpenUrl -> {
                    //todo intent to open url
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    @Composable
    override fun Content() {
        val viewState = viewModel.viewState.collectAsState().value
        val loadingState = viewModel.loadingState.collectAsState().value

        when (viewState) {
            is AuthState.DefaultState -> AuthView(viewState, viewModel::obtainIntent)
        }

        when (loadingState) {
            is LoadingState.Enabled -> LoadingPageBlocker()
            else -> Unit
        }
    }
}