package com.exprnc.winditechnicaltask.presentation.ui.components.bottomnavigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntry
        val currentId = navBackStackEntry?.destination?.id

        BottomNavItem.entries.forEach { item ->
            NavigationBarItem(
                selected = currentId == item.id,
                onClick = {
                    navController.navigate(item.id)
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp, 24.dp),
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                label = { Text(text = item.label) }
            )
        }
    }
}