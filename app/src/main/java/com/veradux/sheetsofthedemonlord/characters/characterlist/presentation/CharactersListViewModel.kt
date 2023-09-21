package com.veradux.sheetsofthedemonlord.characters.characterlist.presentation

import androidx.lifecycle.ViewModel
import com.veradux.sheetsofthedemonlord.characters.characterlist.data.mock.CharactersApiMock
import com.veradux.sheetsofthedemonlord.characters.characterlist.model.DemonLordCharacter

class CharactersListViewModel(private val apiMock: CharactersApiMock = CharactersApiMock()) : ViewModel() {

    fun getCharacters(): List<DemonLordCharacter> {
        return apiMock.getCharacters()
    }

}
