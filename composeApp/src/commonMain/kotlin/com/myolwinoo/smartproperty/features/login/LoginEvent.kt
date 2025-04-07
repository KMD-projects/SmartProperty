package com.myolwinoo.smartproperty.features.login

sealed class LoginEvent {
    object LoginSuccess : LoginEvent()
}