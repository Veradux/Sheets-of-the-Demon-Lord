package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell

@Composable
fun Spell(spell: Spell) {
    Column(Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
        SpellTitle(spell)
        Divider(Modifier.padding(vertical = 4.dp))

        // spell properties
        spell.getPropertiesText().let {
            if (it.isNotEmpty()) {
                Text(makeKeywordsBold(it, Spell.propertyKeywords))
                Divider(Modifier.padding(vertical = 4.dp))
            }
        }

        // spell description
        Text(
            text = makeKeywordsBold(spell.description, Spell.descriptionKeywords),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

private fun makeKeywordsBold(text: String, keywords: List<String>): AnnotatedString = buildAnnotatedString {
    var startIndex = 0
    keywords.filter { text.contains(it) }.forEach { keyword ->
        val indexOfKeyword = text.indexOf(keyword)
        append(text.substring(startIndex, indexOfKeyword))
        startIndex = indexOfKeyword + keyword.length
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append(keyword)
        }
    }
    append(text.substring(startIndex, text.length))
}

@Preview
@Composable
fun SpellPreview() {
    Spell(
        Spell(
            name = "NAME",
            tradition = "TRADITION",
            type = Spell.Type.UTILITY,
            level = 2,
            target = "Target One creature or object within short range",
            description = "This is the spell's description, it contains information on how to use the spell.",
            sourceBook = "Shadow of the Demon Lord"
        )
    )
}