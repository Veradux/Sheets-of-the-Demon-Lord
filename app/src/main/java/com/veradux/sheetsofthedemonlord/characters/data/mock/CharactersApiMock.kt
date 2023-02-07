package com.veradux.sheetsofthedemonlord.characters.data.mock

import com.veradux.sheetsofthedemonlord.characters.data.CharactersApi
import com.veradux.sheetsofthedemonlord.characters.model.DemonLordCharacter
import com.veradux.sheetsofthedemonlord.characters.model.NovicePath

class CharactersApiMock : CharactersApi {

    override fun getCharacters(): List<DemonLordCharacter> {
        return listOf(
            DemonLordCharacter(name = "Sock", level = 1, ancestryName = "Goblin", novicePath = NovicePath.Rogue),
            DemonLordCharacter(name = "Icathus", level = 1, ancestryName = "Human", novicePath = NovicePath.Magician),
            DemonLordCharacter(name = "Krush", level = 10, ancestryName = "Orc", novicePath = NovicePath.Warrior),
        )
    }
}
