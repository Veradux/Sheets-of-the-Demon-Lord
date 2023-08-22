package com.veradux.sheetsofthedemonlord.gameinfo.spells.data

import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.spells

interface SpellsRepository {
    fun getSpells(): List<Spell>
}

class SpellsRepositoryMock : SpellsRepository {
    // TODO this value spells is taken from a global val in the main activity. Fix that when the database is made.
    override fun getSpells(): List<Spell> = spells
}
