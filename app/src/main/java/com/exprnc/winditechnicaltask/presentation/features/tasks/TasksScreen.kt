package com.exprnc.winditechnicaltask.presentation.features.tasks

import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Screen

class TasksScreen : Screen<Int>(
    route = R.id.tasks_nav_graph,
    requestKey = TAG
) {
    companion object {
        const val TAG = "TasksScreen"
    }
}