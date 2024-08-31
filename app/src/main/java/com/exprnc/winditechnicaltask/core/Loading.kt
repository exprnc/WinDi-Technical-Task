package com.exprnc.winditechnicaltask.core

sealed interface LoadingState {
    class Enabled(val data: LoadingData? = null) : LoadingState
    data object Disabled : LoadingState
}

data class LoadingData(
    val title: String = "",
    val subtitle: String = "",
    val type: LoadingType = LoadingType.Transparent
)

enum class LoadingType {
    Shimmer,
    Transparent;
}