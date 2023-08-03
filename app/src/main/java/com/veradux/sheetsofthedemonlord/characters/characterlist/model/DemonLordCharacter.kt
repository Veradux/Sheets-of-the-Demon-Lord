package com.veradux.sheetsofthedemonlord.characters.characterlist.model

data class DemonLordCharacter(
    val name: String,
    val level: Int,
    val ancestryName: String,
    val novicePath: NovicePath
)
