package com.veradux.sheetsofthedemonlord.characters.presentation

import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.characters.model.NovicePath

fun mapNovicePathToDrawable(path: NovicePath) = when (path) {
    NovicePath.Cleric -> R.drawable.bg_cleric
    NovicePath.Warrior -> R.drawable.bg_warrior
    NovicePath.Rogue -> R.drawable.bg_rogue
    NovicePath.Magician -> R.drawable.bg_magician
}
