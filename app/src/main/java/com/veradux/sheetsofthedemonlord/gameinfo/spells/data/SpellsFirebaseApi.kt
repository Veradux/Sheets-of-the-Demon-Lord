package com.veradux.sheetsofthedemonlord.gameinfo.spells.data

import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.util.ResultState
import com.veradux.sheetsofthedemonlord.util.addFirebaseListener

class SpellsFirebaseApi : SpellsApi {

    override fun getSpells(onResultReceived: (ResultState<List<Spell>>) -> Unit) {
        addFirebaseListener("spells", onResultReceived)
    }
}
