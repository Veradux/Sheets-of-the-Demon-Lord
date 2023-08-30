package com.veradux.sheetsofthedemonlord.characters.characterlist.data

import com.veradux.sheetsofthedemonlord.characters.characterlist.model.DemonLordCharacter

// TODO rename this to repository
interface CharactersApi {

    fun getCharacters() : List<DemonLordCharacter>
}
