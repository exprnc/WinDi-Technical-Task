package com.exprnc.winditechnicaltask.presentation.features.chats

import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Screen

class ChatsScreen : Screen<Int>(
    route = R.id.chats_nav_graph,
    requestKey = TAG
) {
    companion object {
        const val TAG = "ChatsScreen"
    }
}