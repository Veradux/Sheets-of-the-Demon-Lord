package com.veradux.sheetsofthedemonlord.gameinfo

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.util.Locale

val traditionDescriptions = mutableMapOf<String, String>()

fun convertPdfSpellsToJson(inputStream: InputStream) {
    val plainTextLines = readInputStream(inputStream)
    logLines(plainTextLines, 1)

    val linesWithoutPageNumbers = removePageNumberLines(plainTextLines)
    logLines(linesWithoutPageNumbers, 2)

    val linesWithoutTraditionDescriptions = removeTraditionDescriptions(linesWithoutPageNumbers)
    logLines(linesWithoutTraditionDescriptions, 3)
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
    return !any { it.isLowerCase() }
}

private fun removeTraditionDescriptions(lines: List<String>): List<String> {
    val traditions = listOf(
        "Arcana", "Air", "Battle", "Alteration", "Conjuration", "Celestial", "Curse", "Chaos",
        "Divination", "Destruction", "Enchantment", "Earth", "Forbidden", "Fire", "Illusion", "Life", "Necromancy",
        "Nature", "Protection", "Primal", "Rune", "Song", "Shadow", "Storm", "Technomancy", "Theurgy",
        "Teleportation", "Transformation", "Time", "Water",
    ).sorted()

    val newLines = lines.toMutableList()

    traditions.forEach { tradition ->
        val start = newLines.indexOf(tradition)
        // the end is the first spell name for that tradition, which comes right after the tradition description
        // the -1 is so that the end itself, which we are looking for, is not removed
        val end = -1 + newLines.indexOf(newLines.find {
            it.contains(tradition.uppercase(Locale.ROOT)) && it.areAllExistingCharactersUpperCase() && it.last() == '0'
        })

        if (start != -1 && end != -1) {
            // extract the tradition descriptions to save them in a map
            // and then remove the description from the lines which should contain only spells
            traditionDescriptions[tradition] = newLines
                .slice(start + 1..end)
                .reduceOrNull { acc, s -> "$acc\n$s" } ?: ""
            newLines.removeSlice(start, end)
        }
    }
    return newLines
}

private fun logLines(lines: List<String>, step: Int) {
    Log.d("pdf $step", lines.reduce { acc, s -> "$acc\n$s" })
}
