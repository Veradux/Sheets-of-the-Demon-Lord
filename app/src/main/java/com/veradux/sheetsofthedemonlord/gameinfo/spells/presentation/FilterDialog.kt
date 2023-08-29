package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell.Companion.traditions
import com.veradux.sheetsofthedemonlord.ui.composables.FilterCategory

sealed class SpellFilterCategory(
    val id: String,
    @StringRes val nameRes: Int,
    val icon: ImageVector
) {
    object Traditions : SpellFilterCategory("Traditions", R.string.traditions, Icons.Rounded.Star)
    object Level : SpellFilterCategory("Level", R.string.level, Icons.Rounded.Star)
    object Properties : SpellFilterCategory("Properties", R.string.properties, Icons.Rounded.Star)
    object SourceBooks : SpellFilterCategory("SourceBooks", R.string.source_book, Icons.Rounded.Star)
}

@Composable
fun FilterDialog(
    allFilterCategories: Map<SpellFilterCategory, List<String>>,
    selectedFilterCategories: Map<SpellFilterCategory, List<String>>,
    onDialogClose: (Map<SpellFilterCategory, List<String>>) -> Unit,
    onFilterSelected: (String, SpellFilterCategory) -> Unit
) {
    Dialog(
        onDismissRequest = { onDialogClose(selectedFilterCategories) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row {
                    Text(
                        text = stringResource(R.string.filter_dialog_screen_title),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { onDialogClose(selectedFilterCategories) },
                        content = { Text(text = stringResource(R.string.apply)) }
                    )
                }

                allFilterCategories.forEach { (category, filters) ->
                    FilterCategory(
                        icon = category.icon,
                        categoryNameResource = category.nameRes,
                        allFilters = filters,
                        selectedFilters = selectedFilterCategories[category] ?: emptyList(),
                        onFilterSelected = { filter -> onFilterSelected(filter, category) }
                    )
                    if (allFilterCategories.keys.last() != category) {
                        Divider(Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FilterDialogPreview() {
    val allFilters = remember {
        mapOf(
            SpellFilterCategory.Traditions to traditions.map { it.name },
            SpellFilterCategory.Level to listOf("0", "1", "2", "3", "5", "6", "7", "8", "9", "10"),
            SpellFilterCategory.Properties to listOf("Requirement", "Target", "Area", "Duration"),
            SpellFilterCategory.SourceBooks to listOf("Shadow of the Demon Lord", "Occult Philosophy")
        )
    }
    val selectedFilters = rememberSaveable {
        mapOf(
            SpellFilterCategory.Traditions to mutableStateListOf(""),
            SpellFilterCategory.Level to mutableStateListOf("3", "7", "10"),
            SpellFilterCategory.Properties to mutableStateListOf(""),
            SpellFilterCategory.SourceBooks to mutableStateListOf("")
        )
    }

    FilterDialog(
        allFilterCategories = allFilters,
        selectedFilterCategories = selectedFilters,
        onDialogClose = { },
        onFilterSelected = { filter, category ->
            val currentCategory = selectedFilters[category]
            if (currentCategory?.contains(filter) == true)
                currentCategory.remove(filter)
            else currentCategory?.add(filter)
        }
    )
}
