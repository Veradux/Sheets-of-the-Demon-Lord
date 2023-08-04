package com.veradux.sheetsofthedemonlord.gameinfo

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream

fun convertPdfSpellsToJson(inputStream: InputStream) {
    val reader = BufferedReader(inputStream.reader())
    val plainTextLines = mutableListOf<String>()
    reader.use {
        var line = it.readLine()
        while (line != null) {
            plainTextLines.add(line)
            line = it.readLine()
        }
    }
    Log.d("pdf", plainTextLines.toString())
}
