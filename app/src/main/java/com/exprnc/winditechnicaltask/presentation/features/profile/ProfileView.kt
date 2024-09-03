package com.exprnc.winditechnicaltask.presentation.features.profile

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.domain.model.Avatars
import com.exprnc.winditechnicaltask.domain.model.User
import com.exprnc.winditechnicaltask.presentation.ui.components.bottomnavigation.BottomNavigationBar
import com.exprnc.winditechnicaltask.utils.BASE_URL
import com.exprnc.winditechnicaltask.utils.toString
import java.util.Calendar
import java.util.Date

@Preview(showSystemUi = true)
@Composable
private fun PreviewProfileView() {
    ProfileView(
        navController = NavController(LocalContext.current),
        viewState = ProfileState.DefaultState(
            user = User(
                id = 0,
                name = "Альфир",
                username = "exprnc7",
                birthday = Date(0),
                city = "Казань",
                vk = "",
                instagram = "",
                status = "",
                avatar = "",
                phone = "+79639090238",
                avatars = Avatars(avatar = "", bigAvatar = "", miniAvatar = ""),
                fileName = "",
                base64 = ""
            ),
            expanded = false
        )
    )
}

@Composable
fun ProfileView(
    navController: NavController,
    viewState: ProfileState.DefaultState,
    onIntent: (Intent) -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(viewState, onIntent) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(
                        top = innerPadding.calculateTopPadding()
                    )
            ) {
                if (viewState.user.avatar.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (viewState.user.name.firstOrNull() ?: "._.").toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                        )
                    }
                } else {
                    AsyncImage(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        model = BASE_URL + "/" + viewState.user.avatar,
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    modifier = Modifier.padding(bottom = 24.dp, start = 16.dp),
                    text = viewState.user.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
            HorizontalDivider(modifier = Modifier.padding(top = 32.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 32.dp, end = 32.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.profile_account_title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
                val birthday = viewState.user.birthday.toString("dd.MM.yyyy")
                ProfileInfoItem(viewState.user.phone, stringResource(id = R.string.profile_phone_subtitle))
                ProfileInfoItem(viewState.user.username, stringResource(id = R.string.profile_username_subtitle))
                ProfileInfoItem(viewState.user.city, stringResource(id = R.string.profile_city_subtitle))
                ProfileInfoItem(birthday, stringResource(id = R.string.profile_birthday_subtitle))
                ProfileInfoItem(getZodiacSign(viewState.user.birthday), stringResource(id = R.string.profile_zodiac_subtitle))
            }
        }
    }
}

@Composable
private fun ProfileInfoItem(title: String, subtitle: String) {
    Text(
        modifier = Modifier.padding(top = 12.dp),
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    )
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = subtitle,
        fontSize = 14.sp,
        color = Color.Gray,
        fontWeight = FontWeight.Normal,
    )
    HorizontalDivider(modifier = Modifier.padding(top = 12.dp))
}

private fun getZodiacSign(dateOfBirth: Date): String {
    val calendar = Calendar.getInstance()
    calendar.time = dateOfBirth

    val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
    val year = calendar.get(Calendar.YEAR)

    return when (dayOfYear) {
        in getDayOfYear(year, 3, 21)..getDayOfYear(year, 4, 19) -> "Овен"
        in getDayOfYear(year, 4, 20)..getDayOfYear(year, 5, 20) -> "Телец"
        in getDayOfYear(year, 5, 21)..getDayOfYear(year, 6, 20) -> "Близнецы"
        in getDayOfYear(year, 6, 21)..getDayOfYear(year, 7, 22) -> "Рак"
        in getDayOfYear(year, 7, 23)..getDayOfYear(year, 8, 22) -> "Лев"
        in getDayOfYear(year, 8, 23)..getDayOfYear(year, 9, 22) -> "Дева"
        in getDayOfYear(year, 9, 23)..getDayOfYear(year, 10, 22) -> "Весы"
        in getDayOfYear(year, 10, 23)..getDayOfYear(year, 11, 21) -> "Скорпион"
        in getDayOfYear(year, 11, 22)..getDayOfYear(year, 12, 21) -> "Стрелец"
        in getDayOfYear(year, 12, 22)..getDayOfYear(year, 1, 19) -> "Козерог"
        in getDayOfYear(year, 1, 20)..getDayOfYear(year, 2, 18) -> "Водолей"
        else -> "Рыбы"
    }
}

private fun getDayOfYear(year: Int, month: Int, day: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, day)
    return calendar.get(Calendar.DAY_OF_YEAR)
}

private fun getFileNameFromUri(context: Context, uri: Uri): String? {
    var fileName: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
    }

    if (fileName == null) {
        fileName = uri.path
        val cut = fileName?.lastIndexOf('/')
        if (cut != -1 && cut != null) {
            fileName = fileName?.substring(cut + 1)
        }
    }

    return fileName
}

private fun getBase64FromUri(context: Context, uri: Uri): String? {
    val inputStream = context.contentResolver.openInputStream(uri)
    val byteArray = inputStream?.readBytes()
    return if (byteArray != null) {
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    } else {
        null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    viewState: ProfileState.DefaultState,
    onIntent: (Intent) -> Unit = {},
) {
    val context = LocalContext.current
    val singleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                val fileName = getFileNameFromUri(context, uri)
                val base64 = getBase64FromUri(context, uri)
                if(fileName != null && base64 != null) {
                    onIntent(ProfileIntent.OnAvatarChanged(fileName, base64))
                }
            }
        }
    )
    TopAppBar(
        title = { },
        actions = {
            IconButton(onClick = { onIntent(ProfileIntent.OnMoreMenuPressed) }) {
                Icon(
                    painter = painterResource(id = R.drawable.more_horiz),
                    contentDescription = ""
                )
            }
            DropdownMenu(
                expanded = viewState.expanded,
                onDismissRequest = { onIntent(ProfileIntent.OnMoreMenuPressed) }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.edit_button)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = ""
                        )
                    },
                    onClick = {
                        onIntent(ProfileIntent.OnEditPressed)
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.choose_photo)) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.add_a_photo),
                            contentDescription = ""
                        )
                    },
                    onClick = {
                        singleImagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        onIntent(ProfileIntent.OnMoreMenuPressed)
                    }
                )
            }
        }
    )
}