package com.exprnc.winditechnicaltask.presentation.features.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.findNavController
import coil.compose.rememberImagePainter
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.BaseFragment
import com.exprnc.winditechnicaltask.presentation.ui.components.bottomnavigation.BottomNavigationBar

data class Chat(
    val name: String,
    val message: String,
    val time: String,
    val unreadCount: Int = 0
)

class ChatsFragment : BaseFragment() {

    private val navController by lazy { findNavController() }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavigationBar(navController = navController) },
            topBar = {
                TopAppBar(
                    title = { Text("Chats") },
                    actions = {
                        IconButton(onClick = {  }) {
                            Icon(Icons.Default.Search, contentDescription = "")
                        }
                        IconButton(onClick = {  }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "")
                        }
                    }
                )
            }
        ) { innerPadding ->
            val chatList = listOf(
                Chat("John Doe", "Hello!", "10:45 AM", 2),
                Chat("Jane Smith", "See you soon.", "09:30 AM", 0),
            )
            ChatList(chats = chatList, innerPadding)
        }
    }
    @Composable
    fun ChatList(chats: List<Chat>, innerPadding: PaddingValues) {
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(chats) { chat ->
                ChatItem(chat = chat)
                HorizontalDivider()
            }
        }
    }

    @Composable
    private fun ChatItem(chat: Chat) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = chat.name, fontWeight = FontWeight.Bold)
                Text(
                    text = chat.message,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(text = chat.time, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
                if (chat.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Red, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(chat.unreadCount.toString())
                    }
                }
            }
        }
    }
}