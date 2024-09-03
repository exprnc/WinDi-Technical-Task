package com.exprnc.winditechnicaltask.presentation.features.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.findNavController
import com.exprnc.winditechnicaltask.core.BaseFragment
import com.exprnc.winditechnicaltask.presentation.ui.components.bottomnavigation.BottomNavigationBar

class TasksFragment : BaseFragment() {

    private val navController by lazy { findNavController() }

    @Composable
    override fun Content() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Заглушка", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}