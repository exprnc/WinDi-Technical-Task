package com.exprnc.winditechnicaltask.presentation.ui.components.floatingaction

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun FloatingAction(icon: ImageVector, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = ""
        )
    }
}