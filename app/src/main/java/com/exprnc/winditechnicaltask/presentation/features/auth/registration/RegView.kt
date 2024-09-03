package com.exprnc.winditechnicaltask.presentation.features.auth.registration

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Intent

@Preview(showSystemUi = true)
@Composable
private fun PreviewRegView() {
    RegView(
        RegState.DefaultState(phone = "+79639090238", name = "", username = "")
    )
}

@Composable
fun RegView(
    viewState: RegState.DefaultState,
    onIntent: (Intent) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        floatingActionButton = {
            FloatingAction(icon = Icons.AutoMirrored.Filled.ArrowForward) {
                onIntent(RegIntent.OnNextPressed)
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
                RegTitles()
                RegInputFields(viewState, onIntent)
            }
        }
    }
}

@Composable
private fun RegInputFields(
    viewState: RegState.DefaultState,
    onIntent: (Intent) -> Unit = {}
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        enabled = false,
        value = viewState.phone,
        onValueChange = {},
        readOnly = true,
        label = { Text(text = stringResource(id = R.string.phone_text_field_label))}
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = viewState.name,
        onValueChange = { newName -> onIntent(RegIntent.OnNameInput(newName)) },
        label = { Text(stringResource(id = R.string.reg_text_field_name_label)) },
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = viewState.username,
        onValueChange = { newUsername -> onIntent(RegIntent.OnUsernameInput(newUsername)) },
        label = { Text(stringResource(id = R.string.reg_text_field_username_label)) },
    )
}

@Composable
private fun RegTitles() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.reg_title),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.reg_subtitle),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun FloatingAction(icon: ImageVector, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = ""
        )
    }
}