package com.example.threadssocialmediaapp.views.auth.intro

sealed class IntroEvents {
    object NavigateToCreateAccount : IntroEvents()
    object NavigateToDashboard : IntroEvents()
    object None:IntroEvents()
}