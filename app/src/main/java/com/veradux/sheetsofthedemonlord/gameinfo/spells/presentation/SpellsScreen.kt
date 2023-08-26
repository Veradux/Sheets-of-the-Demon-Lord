package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.SpellFilterCategories
import com.veradux.sheetsofthedemonlord.ui.composables.ScreenWithScrollableTopBar

@Composable
fun SpellsScreen(viewModel: SpellsScreenViewModel = viewModel()) {
    val isFilterDialogOpen by viewModel.isFilterDialogOpen.collectAsState(initial = false)
    val filteredSpells by viewModel.filteredSpells.collectAsState()
    val spellFilters by viewModel.spellFilters.collectAsState()
    var isActive by rememberSaveable { mutableStateOf(false) }

    ScreenWithScrollableTopBar(
        topBarHeight = 64.dp,
        topBar = { offset ->
            SpellsSearchBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset { offset },
                query = viewModel.searchBarText.collectAsState().value,
                onQueryChange = viewModel::onSearchBarTextChange,
                isActive = isActive,
                setActiveStateTo = { isActive = it },
                setFilterDialogVisibilityStateTo = viewModel::setFilterDialogVisibilityStateTo
            ) {
                Text(
                    text = stringResource(R.string.x_spells_found, filteredSpells.count()),
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .align(Alignment.End)
                )
                LazyColumn {
                    items(filteredSpells) { spell ->
                        SpellTitle(spell, modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp))
                    }
                }
            }
        }
    ) { paddingValues ->
        AnimatedVisibility(filteredSpells.isEmpty()) {
            Text(
                text = stringResource(R.string.no_spells_found),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 120.dp)
                    .align(Alignment.Center)
            )
        }
        // TODO fix enter transition
        AnimatedVisibility(filteredSpells.isNotEmpty()) {
            LazyColumn(contentPadding = paddingValues) {
                items(filteredSpells) { Spell(it) }
            }
        }

        if (isFilterDialogOpen) {
            Dialog(
                onDismissRequest = { viewModel.setFilterDialogVisibilityStateTo(false) },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Surface {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        IconButton(
                            modifier = Modifier.align(Alignment.End),
                            onClick = { viewModel.setFilterDialogVisibilityStateTo(false) }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Filter spells")
                        }

                        FilterCategory(
                            R.string.traditions,
                            Icons.Default.Star,
                            spellFilters.traditionFilters
                        ) { toggle ->
                            viewModel.updateSpellFilters(spellFilters.traditionFilters, toggle) {
                                spellFilters.copy(traditionFilters = it)
                            }
                        }
                        Divider(modifier = Modifier.padding(8.dp))
                        FilterCategory(R.string.level, Icons.Default.Star, spellFilters.levelFilters) { toggle ->
                            viewModel.updateSpellFilters(spellFilters.levelFilters, toggle) {
                                spellFilters.copy(levelFilters = it)
                            }
                        }

                        Divider(modifier = Modifier.padding(8.dp))
                        FilterCategory(
                            R.string.properties,
                            Icons.Default.Star,
                            spellFilters.propertyFilters
                        ) { toggle ->
                            viewModel.updateSpellFilters(spellFilters.propertyFilters, toggle) {
                                spellFilters.copy(propertyFilters = it)
                            }
                        }
                        Divider(modifier = Modifier.padding(8.dp))
                        FilterCategory(
                            R.string.source_book,
                            Icons.Default.Star,
                            spellFilters.sourceBookFilters
                        ) { toggle ->
                            viewModel.updateSpellFilters(spellFilters.sourceBookFilters, toggle) {
                                spellFilters.copy(sourceBookFilters = it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterCategory(
    @StringRes categoryNameResource: Int,
    icon: ImageVector,
    filters: List<SpellFilterCategories.Filter>,
    onClick: (SpellFilterCategories.Filter) -> Unit
) {
    Row {
        Icon(icon, contentDescription = null, modifier = Modifier.align(Alignment.CenterVertically))
        Text(
            text = stringResource(categoryNameResource),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp),
        )
    }

    FlowRow {
        filters.forEach { filter ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 4.dp),
                label = { Text(filter.name) },
                onClick = { onClick(filter) },
                selected = filter.isEnabled,
            )
        }
    }
}
