package com.veradux.sheetsofthedemonlord.characters.charactercreation.data

import com.veradux.sheetsofthedemonlord.characters.charactercreation.model.Ancestry

interface CharacterCreationApi {

    fun getAncestries() : List<Ancestry>
}