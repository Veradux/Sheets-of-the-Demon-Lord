package com.veradux.sheetsofthedemonlord.characters.data

import com.veradux.sheetsofthedemonlord.characters.model.DemonLordCharacter

interface CharactersApi {

    fun getCharacters() : List<DemonLordCharacter>
}
