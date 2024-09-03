package com.exprnc.winditechnicaltask.presentation.ui.components.bottomnavigation

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.exprnc.winditechnicaltask.R

enum class BottomNavItem(
    @IdRes val id: Int,
    val label: String,
    val icon: ImageVector
) {
    Chats(R.id.chats_nav_graph, "Chats", Icons.Filled.Home),
    Tasks(R.id.tasks_nav_graph, "Tasks", Icons.AutoMirrored.Filled.List),
    Profile(R.id.profile_nav_graph, "Profile", Icons.Filled.AccountCircle),
}