package com.veradux.sheetsofthedemonlord

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.veradux.sheetsofthedemonlord.characters.CharactersScreen

class NavDrawerViewModel : ViewModel() {

    private var selectedButtonState: MutableState<String> = mutableStateOf(CharactersScreen.LIST)

    fun updateSelectedButtonState(route: String) {
        selectedButtonState.value = route
    }

    fun isButtonSelected(route: String): Boolean = route == selectedButtonState.value

}
