package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veradux.sheetsofthedemonlord.R
import com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation.SpellsScreenViewModel.AllSpellsState
import com.veradux.sheetsofthedemonlord.ui.composables.ScreenWithScrollableTopBar

@Composable
fun SpellsScreen(viewModel: SpellsScreenViewModel = viewModel()) {
    val isFilterDialogOpen by viewModel.isFilterDialogOpen.collectAsState(initial = false)
    val spellsState by viewModel.allSpellsState.collectAsState()
    val filteredSpells by viewModel.filteredSpells.collectAsState()
    var isActive by rememberSaveable { mutableStateOf(false) }

    ScreenWithScrollableTopBar(
        topBarHeight = 64.dp,
        topBar = { offset ->
            SpellsSearchBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset { offset },
                query = viewModel.searchQuery.collectAsState().value,
                onQueryChange = viewModel::onSearchQueryChange,
                isActive = isActive,
                setActiveStateTo = { isActive = it },
                onFilterButtonClick = { viewModel.setFilterDialogVisibilityTo(true) },
                spells = filteredSpells
            )
        }
    ) { paddingValues ->
        when (spellsState) {
            AllSpellsState.ERROR -> SpellsScreenMessage(R.string.error_loading_spells)
            AllSpellsState.LOADING -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            AllSpellsState.LOADED ->
                if (filteredSpells.isNotEmpty()) {
                    LazyColumn(contentPadding = paddingValues) {
                        items(filteredSpells) { Spell(it) }
                    }
                } else {
                    SpellsScreenMessage(R.string.no_spells_found)
                }
        }

        if (isFilterDialogOpen) {
            FilterDialogScreen(
                filterCategories = viewModel.filterCategories.getFilterCategories(),
                onDismiss = { viewModel.setFilterDialogVisibilityTo(false) }
            )
        }
    }
}

@Composable
private fun BoxScope.SpellsScreenMessage(@StringRes messageId: Int) {
    Text(
        text = stringResource(messageId),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 120.dp)
            .align(Alignment.Center)
    )
}

@Preview
@Composable
fun SpellScreenPreview() {
    SpellsScreen()
}
