package com.veradux.sheetsofthedemonlord.gameinfo.spells.data

import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.util.ResultState

interface SpellsApi {
    fun getSpells(onResultReceived: (ResultState<List<Spell>>) -> Unit)
}
