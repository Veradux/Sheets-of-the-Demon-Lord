package com.veradux.sheetsofthedemonlord.characters.characterlist.presentation

import androidx.lifecycle.ViewModel
import com.veradux.sheetsofthedemonlord.characters.characterlist.data.mock.CharactersApiMock
import com.veradux.sheetsofthedemonlord.characters.characterlist.model.DemonLordCharacter

// TODO figure out if this instantiation in the constructor is correct. Probably not.
class CharactersListViewModel(private val apiMock: CharactersApiMock = CharactersApiMock()) : ViewModel() {

    fun getCharacters(): List<DemonLordCharacter> {
        return apiMock.getCharacters()
    }

}
