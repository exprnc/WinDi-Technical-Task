package com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode.VerificationCodeViewModel.Companion.CODE_LENGTH

@Preview(showSystemUi = true)
@Composable
private fun PreviewVerificationCodeView() {
    VerificationCodeView(
        viewState = VerificationCodeState.DefaultState(
            phone = "+79639090238",
            code = "133337"
        )
    )
}

@Composable
fun VerificationCodeView(
    viewState: VerificationCodeState.DefaultState,
    onIntent: (Intent) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize().imePadding(),
        floatingActionButton = {
            FloatingAction(icon = Icons.AutoMirrored.Filled.ArrowForward) {
                onIntent(VerificationCodeIntent.OnNextPressed)
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
                VerificationCodeTitles(viewState.phone)

                OTPTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    otpCode = viewState.code,
                    onOtpCodeChange = { newOtpCode ->
                        onIntent(VerificationCodeIntent.OnCodeInput(newOtpCode))
                    }
                )
            }
        }
    }
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

@Composable
private fun VerificationCodeTitles(phone: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.verification_code_title),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.verification_code_subtitle, phone),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun OTPTextField(
    modifier: Modifier,
    otpCode: String,
    onOtpCodeChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = otpCode,
        onValueChange = { newValue ->
            onOtpCodeChange(newValue)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OTPNumbers(otpCode)
        }
    }
}

@Composable
private fun OTPNumbers(otpCode: String) {
    repeat(CODE_LENGTH) { index ->
        val number = when {
            index >= otpCode.length -> ""
            else -> otpCode[index].toString()
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(height = 60.dp, width = 50.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            Text(
                text = number,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}