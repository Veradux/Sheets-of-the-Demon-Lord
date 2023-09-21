package com.veradux.sheetsofthedemonlord.characters.characterlist.data.mock

import com.veradux.sheetsofthedemonlord.characters.characterlist.data.CharactersApi
import com.veradux.sheetsofthedemonlord.characters.characterlist.model.DemonLordCharacter
import com.veradux.sheetsofthedemonlord.characters.characterlist.model.NovicePath

class CharactersApiMock : CharactersApi {

    // TODO impl a local resources version of this. The characters should be saved locally for each user.
    override fun getCharacters(): List<DemonLordCharacter> {
        return listOf(
            DemonLordCharacter(name = "Sock", level = 1, ancestryName = "Goblin", novicePath = NovicePath.Rogue),
            DemonLordCharacter(name = "Icathus", level = 1, ancestryName = "Human", novicePath = NovicePath.Magician),
            DemonLordCharacter(name = "Krush", level = 10, ancestryName = "Orc", novicePath = NovicePath.Warrior),
            DemonLordCharacter(name = "Vaelaranius Aurelius", level = 10, ancestryName = "Orc", novicePath = NovicePath.Cleric),
            DemonLordCharacter(name = "Sock", level = 1, ancestryName = "Goblin", novicePath = NovicePath.Rogue),
            DemonLordCharacter(name = "Icathus", level = 1, ancestryName = "Human", novicePath = NovicePath.Magician),
            DemonLordCharacter(name = "Krush", level = 10, ancestryName = "Orc", novicePath = NovicePath.Warrior),
            DemonLordCharacter(name = "Vaelaranius Aurelius", level = 10, ancestryName = "Orc", novicePath = NovicePath.Cleric),
            DemonLordCharacter(name = "Sock", level = 1, ancestryName = "Goblin", novicePath = NovicePath.Rogue),
            DemonLordCharacter(name = "Icathus", level = 1, ancestryName = "Human", novicePath = NovicePath.Magician),
            DemonLordCharacter(name = "Krush", level = 10, ancestryName = "Orc", novicePath = NovicePath.Warrior),
            DemonLordCharacter(name = "Vaelaranius Aurelius", level = 10, ancestryName = "Orc", novicePath = NovicePath.Cleric),
            DemonLordCharacter(name = "Sock", level = 1, ancestryName = "Goblin", novicePath = NovicePath.Rogue),
            DemonLordCharacter(name = "Icathus", level = 1, ancestryName = "Human", novicePath = NovicePath.Magician),
            DemonLordCharacter(name = "Krush", level = 10, ancestryName = "Orc", novicePath = NovicePath.Warrior),
            DemonLordCharacter(name = "Vaelaranius Aurelius", level = 10, ancestryName = "Orc", novicePath = NovicePath.Cleric),
            DemonLordCharacter(name = "Sock", level = 1, ancestryName = "Goblin", novicePath = NovicePath.Rogue),
            DemonLordCharacter(name = "Icathus", level = 1, ancestryName = "Human", novicePath = NovicePath.Magician),
            DemonLordCharacter(name = "Krush", level = 10, ancestryName = "Orc", novicePath = NovicePath.Warrior),
            DemonLordCharacter(name = "Vaelaranius Aurelius", level = 10, ancestryName = "Orc", novicePath = NovicePath.Cleric),
            DemonLordCharacter(name = "Sock", level = 1, ancestryName = "Goblin", novicePath = NovicePath.Rogue),
            DemonLordCharacter(name = "Icathus", level = 1, ancestryName = "Human", novicePath = NovicePath.Magician),
            DemonLordCharacter(name = "Krush", level = 10, ancestryName = "Orc", novicePath = NovicePath.Warrior),
            DemonLordCharacter(name = "Vaelaranius Aurelius", level = 10, ancestryName = "Orc", novicePath = NovicePath.Cleric),
            DemonLordCharacter(name = "Sock", level = 1, ancestryName = "Goblin", novicePath = NovicePath.Rogue),
            DemonLordCharacter(name = "Icathus", level = 1, ancestryName = "Human", novicePath = NovicePath.Magician),
            DemonLordCharacter(name = "Krush", level = 10, ancestryName = "Orc", novicePath = NovicePath.Warrior),
            DemonLordCharacter(name = "Vaelaranius Aurelius", level = 10, ancestryName = "Orc", novicePath = NovicePath.Cleric),
            )
    }
}
