package com.exprnc.winditechnicaltask.presentation.features.profile.editprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.utils.toString
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview(showSystemUi = true)
@Composable
private fun PreviewEditProfileView() {
    EditProfileView(
        viewState = EditProfileState.DefaultState(
            name = "",
            city = "",
            birthday = Date(0),
            false
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileView(
    viewState: EditProfileState.DefaultState,
    onIntent: (Intent) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar( onIntent) },
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 32.dp)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewState.name,
                onValueChange = { onIntent(EditProfileIntent.OnNameInput(it)) },
                label = { Text(stringResource(id = R.string.reg_text_field_name_label)) },
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewState.city,
                onValueChange = { onIntent(EditProfileIntent.OnCityInput(it)) },
                label = { Text(stringResource(id = R.string.profile_city_subtitle)) },
            )
            Spacer(modifier = Modifier.height(8.dp))
            BirthdayPicker(viewState, onIntent)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayPicker(viewState: EditProfileState.DefaultState, onIntent: (Intent) -> Unit) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = viewState.birthday.time)

    OutlinedTextField(
        value = viewState.birthday.toString("dd.MM.yyyy"),
        onValueChange = { },
        label = { Text(stringResource(id = R.string.profile_birthday_subtitle)) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { onIntent(EditProfileIntent.OnDatePickerPressed) }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = ""
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    )

    if(viewState.showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { onIntent(EditProfileIntent.OnDatePickerPressed) },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onIntent(EditProfileIntent.OnBirthdaySelected(Date(it)))
                    }
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { onIntent(EditProfileIntent.OnDatePickerPressed) }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onIntent: (Intent) -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.edit_profile_title), fontWeight = FontWeight.Bold, fontSize = 20.sp) },
        navigationIcon = {
            IconButton(onClick = { onIntent(Intent.Back) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        actions = {
            IconButton(onClick = { onIntent(EditProfileIntent.OnConfirmPressed) }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = ""
                )
            }
        }
    )
}