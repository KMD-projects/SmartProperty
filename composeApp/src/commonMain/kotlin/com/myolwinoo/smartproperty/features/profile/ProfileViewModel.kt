package com.myolwinoo.smartproperty.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.AccountManager
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val accountManager: AccountManager,
): ViewModel() {

    fun logout() {
        viewModelScope.launch {
            accountManager.removeUser()
        }
    }
}