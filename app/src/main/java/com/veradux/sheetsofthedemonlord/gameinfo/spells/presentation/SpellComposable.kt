package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell

@Composable
fun Spell(spell: Spell) {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
        // spell title
        Row {
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

        Divider(modifier = Modifier.padding(vertical = 4.dp))

        // spell properties
        spell.propertiesText().let {
            if (it.isNotEmpty()) {
                Text(text = makeKeywordsBold(it, Spell.propertyKeywords))
                Divider(modifier = Modifier.padding(vertical = 4.dp))
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