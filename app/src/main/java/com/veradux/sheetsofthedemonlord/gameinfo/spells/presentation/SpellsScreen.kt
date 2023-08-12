package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.veradux.sheetsofthedemonlord.R

@Composable
fun SpellsScreen(viewModel: SpellsScreenViewModel = SpellsScreenViewModel()) {
    var isFilterDialogOpen by remember { mutableStateOf(false) }
    val selectedTraditions = remember { mutableStateListOf<String>() }
    val selectedLevels = remember { mutableStateListOf<String>() }
    val selectedProperties = remember { mutableStateListOf<String>() }
    val selectedSourceBooks = remember { mutableStateListOf<String>() }
    val filteredSpells = remember {
        mutableStateListOf(
            *viewModel.getFilteredSpells(
                selectedTraditions, selectedLevels, selectedProperties, selectedSourceBooks
            ).toTypedArray()
        )
    }

    Column {
        Row(modifier = Modifier.padding(16.dp)) {
            // TODO add a search bar here. To do that, update material3 dependency
            Button(onClick = { isFilterDialogOpen = true }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Filter spells")
            }
        }

        LazyColumn(Modifier.padding(24.dp)) {
            items(filteredSpells) { spell ->
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Row(Modifier.padding(8.dp)) {
                        Text(
                            text = spell.name,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                        )
                        Text(text = " ${spell.tradition} ${spell.type} ${spell.level}")
                    }
                    Divider(modifier = Modifier.padding(horizontal = 8.dp))
                    // TODO instead of replacing this here every time, do it when parsing the spells
                    Text(text = spell.description.replace("\n", ""))
                }
            }
        }
    }

    if (isFilterDialogOpen) {
        val onDialogClose = {
            isFilterDialogOpen = false
            filteredSpells.clear()
            // TODO check if performance of addAll is bad
            filteredSpells.addAll(
                viewModel.getFilteredSpells(
                    selectedTraditions, selectedLevels, selectedProperties, selectedSourceBooks
                )
            )
        }
        Dialog(
            onDismissRequest = { onDialogClose() }, properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Button(modifier = Modifier.align(Alignment.End), onClick = { onDialogClose() }) {
                        Text(text = stringResource(R.string.filter))
                    }

                    FilterCategory(R.string.traditions, selectedTraditions, viewModel.traditionFilters)
                    Divider(modifier = Modifier.padding(8.dp))

                    FilterCategory(R.string.level, selectedLevels, viewModel.levelFilters)
                    Divider(modifier = Modifier.padding(8.dp))

                    FilterCategory(R.string.properties, selectedProperties, viewModel.propertyFilters)
                    Divider(modifier = Modifier.padding(8.dp))

                    FilterCategory(R.string.source_book, selectedSourceBooks, viewModel.sourceBookFilters)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterCategory(
    @StringRes categoryNameResource: Int,
    selectedFilters: SnapshotStateList<String>,
    filters: List<String>
) {
    Text(
        text = stringResource(categoryNameResource),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp),
    )

    FlowRow {
        filters.forEach { filter ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 4.dp),
                label = { Text(filter) },
                onClick = {
                    if (selectedFilters.contains(filter)) {
                        selectedFilters.remove(filter)
                    } else {
                        selectedFilters.add(filter)
                    }
                },
                selected = selectedFilters.contains(filter),
            )
        }
    }
}
