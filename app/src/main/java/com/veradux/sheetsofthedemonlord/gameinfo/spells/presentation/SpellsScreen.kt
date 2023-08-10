package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
//    val filtersState = remember { mutableStateOf(viewModel.filters) }
    // TODO add a search bar on the top of the screen. To do that, update material3 dependency

    Button(onClick = { isFilterDialogOpen = true }) {
        Icon(imageVector = Icons.Default.Build, contentDescription = "Filter spells")
    }

    if (isFilterDialogOpen) {
        Dialog(
            onDismissRequest = { isFilterDialogOpen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                FilterCategory(categoryNameResource = R.string.traditions, filters = viewModel.filters.traditions)
                Divider(modifier = Modifier.padding(8.dp))
                FilterCategory(categoryNameResource = R.string.level, filters = viewModel.filters.level)
                Divider(modifier = Modifier.padding(8.dp))
                FilterCategory(categoryNameResource = R.string.properties, filters = viewModel.filters.properties)
                Divider(modifier = Modifier.padding(8.dp))
                FilterCategory(categoryNameResource = R.string.source_book, filters = viewModel.filters.sourceBook)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterCategory(@StringRes categoryNameResource: Int, filters: List<String>) {
    val selectedFilters = remember { mutableStateListOf<String>() }
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

// TODO add a filter icon on the right of the search which will open an alertDialog
// TODO if all filters are off, show all spells
// TODO in the dialog, have these potential filters implemented with Chips components:
//  tradition,
//  spell level,
//  requirement, area, target, duration
//  book source
