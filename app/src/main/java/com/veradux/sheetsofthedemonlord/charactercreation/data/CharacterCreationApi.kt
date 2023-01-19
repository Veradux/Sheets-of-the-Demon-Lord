package com.veradux.sheetsofthedemonlord.charactercreation.data

import com.veradux.sheetsofthedemonlord.charactercreation.model.Ancestry

interface CharacterCreationApi {

    fun getAncestries() : List<Ancestry>
}