package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell

@Composable
fun SpellTitle(spell: Spell, modifier: Modifier = Modifier) {
    Row(modifier) {
        Text(
            text = spell.name,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = " ${spell.tradition} ${spell.type} ${spell.level}",
            modifier = Modifier.align(Alignment.Bottom)
        )
    }
}
