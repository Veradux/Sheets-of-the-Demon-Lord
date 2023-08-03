package com.veradux.sheetsofthedemonlord.characters.characterlist.data

import com.veradux.sheetsofthedemonlord.characters.characterlist.model.DemonLordCharacter

interface CharactersApi {

    fun getCharacters() : List<DemonLordCharacter>
}
