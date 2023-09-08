package com.veradux.sheetsofthedemonlord.gameinfo.spells.data

import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell

interface SpellsApi {
    fun getSpells(onResultReceived: (List<Spell>?) -> Unit)
}
