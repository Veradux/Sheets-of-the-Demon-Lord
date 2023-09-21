package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellsApi
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellsFirebaseApi
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.SpellFilterCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn

class SpellsScreenViewModel(spellsApi: SpellsApi = SpellsFirebaseApi()) : ViewModel() {

    enum class SpellsFetchedState {
        LOADING, LOADED, ERROR
    }

    private val _spellsFetchedState = MutableStateFlow(SpellsFetchedState.LOADING)
    val spellsFetchedState = _spellsFetchedState.asStateFlow()

    private val _allSpells = MutableStateFlow(emptyList<Spell>())

    private val _isFilterDialogOpen = MutableStateFlow(false)
    val isFilterDialogOpen = _isFilterDialogOpen.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val filterCategories = SpellFilterCategories()

    val filteredSpells: StateFlow<List<Spell>> =
        isFilterDialogOpen
            .filter { !it }
            .combine(_allSpells) { _, spells -> spells.filter { filterCategories.filter(it) } }
            .combine(searchQuery) { spells, query -> spells.filter { it.containsText(query) } }
            .stateIn(viewModelScope, SharingStarted.Lazily, _allSpells.value)

    init {
        spellsApi.getSpells {
            if (it == null) {
                _spellsFetchedState.value = SpellsFetchedState.ERROR
            } else {
                _allSpells.value = it
                _spellsFetchedState.value = SpellsFetchedState.LOADED
            }
        }
    }

    fun onSearchQueryChange(text: String) {
        _searchQuery.value = text
    }

    fun setFilterDialogVisibilityTo(isVisible: Boolean) {
        _isFilterDialogOpen.value = isVisible
    }
}
