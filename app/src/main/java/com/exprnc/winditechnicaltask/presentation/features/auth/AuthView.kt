package com.exprnc.winditechnicaltask.presentation.features.auth

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpitkatiyarprojects.countrypicker.CountryPickerOutlinedTextField
import com.arpitkatiyarprojects.countrypicker.models.CountryDetails
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.presentation.ui.components.floatingaction.FloatingAction

@Preview(showSystemUi = true)
@Composable
private fun PreviewAuthView() {
    AuthView(
        viewState = AuthState.DefaultState(
            phone = "9639090238",
            CountryDetails(
                countryCode = "US",
                countryPhoneNumberCode = "",
                countryName = "Russia",
                countryFlag = 0
            )
        ),
    )
}

@Composable
fun AuthView(
    viewState: AuthState.DefaultState,
    onIntent: (Intent) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize().imePadding(),
        floatingActionButton = {
            FloatingAction(icon = Icons.AutoMirrored.Filled.ArrowForward) {
                onIntent(AuthIntent.OnNextPressed)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                AuthTitles()
                CountryPickerOutlinedTextField(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    mobileNumber = viewState.phone,
                    onMobileNumberChange = { phoneNumber ->
                        onIntent(AuthIntent.OnPhoneInput(field = phoneNumber))
                    },
                    onCountrySelected = { country ->
                        onIntent(AuthIntent.OnRegionSelected(country))
                    },
                    defaultCountryCode = viewState.country.countryCode,
                    label = {
                        Text(text = stringResource(id = R.string.phone_text_field_label))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.phone_text_field_placeholder))
                    },
                )
            }
        }
    }
}

@Composable
private fun AuthTitles() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.auth_title),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.auth_subtitle),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(16.dp))
}