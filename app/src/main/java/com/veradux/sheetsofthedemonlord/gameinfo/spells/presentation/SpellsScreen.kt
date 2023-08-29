package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veradux.sheetsofthedemonlord.R
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
                onOpenDialogEvent = viewModel::onOpenDialogEvent
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
        // A text message when no spells are being shown.
        if (filteredSpells.isEmpty()) {
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
//            FilterDialog(
//                spellFilters,
//                viewModel::onDialogClose
//            )
        }
    }
}
