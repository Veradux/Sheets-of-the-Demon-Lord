package com.veradux.sheetsofthedemonlord.gameinfo

import android.util.Log
import com.google.gson.Gson
import com.veradux.sheetsofthedemonlord.gameinfo.model.Spell
import java.io.BufferedReader
import java.io.InputStream
import java.util.Locale

fun parsePdfSpellsToJson(inputStream: InputStream) {
    val plainTextLines = readInputStream(inputStream)
    logLines(plainTextLines, 1)

    val linesWithoutPageNumbers = removePageNumberLines(plainTextLines)
    logLines(linesWithoutPageNumbers, 2)

    val linesWithoutTraditionDescriptions = extractTraditionDescriptions(linesWithoutPageNumbers)
    logLines(linesWithoutTraditionDescriptions, 3)

    val spells = getSpellsFromLines(linesWithoutTraditionDescriptions)
    Log.d("pdf 4", Gson().toJson(spells))
}

fun getSpellsFromLines(lines: List<String>): List<Spell> {
    val tempLines = lines.toMutableList()
    val spells = mutableListOf<Spell>()

    do {
        val spellTitleIndex = tempLines.indexOf(tempLines.find {
            // We are looking for the first line of each spell, it looks like this for example:
            // STIR THE AIR AIR UTILITY 0
            it.areAllExistingCharactersUpperCase() && it.last().isDigit()
        })
        if (spellTitleIndex == -1) break

        // The -1 is because we are looking for the next spell title line,
        // but we do not want to include it in the data for the current spell that we are getting.
        val lastLineOfSpellIndex = (-1 + tempLines.indexOf(tempLines.find {
            // We are looking for the first line of each spell, it looks like this for example:
            // STIR THE AIR AIR UTILITY 0
            // Additionally, we are looking for a different line than the first spell title index,
            // which naturally should be the next spell title, or the end of the file.
            it.areAllExistingCharactersUpperCase() && it.last().isDigit() && tempLines.indexOf(it) != spellTitleIndex
        })) ?: tempLines.lastIndex

        val spellTitle = tempLines[spellTitleIndex]
        val splitSpellTitle = spellTitle.split(' ')
        val tradition = splitSpellTitle[splitSpellTitle.size - 3]
        val spellType: Spell.Type? = if (spellTitle.contains(Spell.Type.UTILITY.name)) Spell.Type.UTILITY else {
            if (spellTitle.contains(Spell.Type.ATTACK.name)) Spell.Type.ATTACK else null
        }
        val spellDescription = tempLines
            .slice(spellTitleIndex + 1..lastLineOfSpellIndex)
            .reduceOrNull { acc, s -> "$acc\n$s" }
        spells.add(
            Spell(
                name = spellTitle.substring(0, spellTitle.lastIndexOf(tradition) - 1),
                tradition = tradition,
                // We want this to crash if its null because that would mean the algorithm doesn't work.
                // If it crashes, it most likely means that the spell title index could be wrong,
                // or that there are spell types other than ATTACK and UTILITY.
                type = spellType!!,
                level = spellTitle.last().digitToInt(),
                description = spellDescription ?: "oof"
            )
        )

        tempLines.removeAt(spellTitleIndex)
    } while (spellTitleIndex != -1)

    return spells
}

private fun readInputStream(inputStream: InputStream): List<String> {
    val reader = BufferedReader(inputStream.reader())
    val plainTextLines = mutableListOf<String>()
    reader.use {
        var line = it.readLine()
        while (line != null) {
            plainTextLines.add(line)
            line = it.readLine()
        }
    }
    return plainTextLines
}

private fun removePageNumberLines(lines: List<String>): List<String> {
    val newLines = lines.toMutableList()
    var index = lines.indexOf("shadow of the demon lord")
    while (index != -1) {
        newLines.removeSlice(index - 2, index + 1)
        index = newLines.indexOf("shadow of the demon lord")
    }
    return newLines
}

private fun <E> MutableList<E>.removeSlice(start: Int, end: Int) {
    for (i in end downTo start) {
        removeAt(i)
    }
}

private fun String.areAllExistingCharactersUpperCase(): Boolean {
    // This checks if any lower case characters exist instead of checking if all characters are upper case,
    // because the function should return true when digits and spaces exist in the string also.
    return !any { it.isLowerCase() }
}

private fun extractTraditionDescriptions(lines: List<String>): List<String> {
    val newLines = lines.toMutableList()

    Spell.traditions.forEach { tradition ->
        val start = newLines.indexOf(tradition.name)
        // the end is the first spell name for that tradition, which comes right after the tradition description
        // the -1 is so that the end itself is not removed by the removeSlice function
        val end = -1 + newLines.indexOf(newLines.find {
            // we are looking for the first line of the first spell for this tradition, it looks like this for example:
            // STIR THE AIR AIR UTILITY 0
            it.contains(tradition.name.uppercase(Locale.ROOT)) && it.areAllExistingCharactersUpperCase() && it.last() == '0'
        })

        if (start != -1 && end != -1) {
            // extract the tradition descriptions to save them,
            // and then remove the description from the lines which should contain only spells
            tradition.description = newLines.slice(start + 1..end).reduce { acc, s -> "$acc\n$s" }
            newLines.removeSlice(start, end)
        }
    }
    return newLines
}

private fun logLines(lines: List<String>, step: Int) {
    Log.d("pdf $step", lines.reduce { acc, s -> "$acc\n$s" })
}
