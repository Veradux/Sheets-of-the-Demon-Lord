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
import kotlinx.coroutines.flow.stateIn

class SpellsScreenViewModel(spellsApi: SpellsApi = SpellsFirebaseApi()) : ViewModel() {

    enum class AllSpellsState {
        LOADING, LOADED, ERROR
    }

    private val _allSpellsState = MutableStateFlow(AllSpellsState.LOADING)
    val allSpellsState = _allSpellsState.asStateFlow()

    private val _allSpells = MutableStateFlow(emptyList<Spell>())

    private val _isFilterDialogOpen = MutableStateFlow(false)
    val isFilterDialogOpen = _isFilterDialogOpen.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val filterCategories = SpellFilterCategories()

    private var oldFilteredSpells = emptyList<Spell>()
    val filteredSpells: StateFlow<List<Spell>> =
        combine(searchQuery, _allSpells, isFilterDialogOpen) { query, spells, isDialogOpen ->
            if (!isDialogOpen) {
                oldFilteredSpells = spells.filter { it.containsText(query) && filterCategories.filter(it) }
            }
            oldFilteredSpells
        }.stateIn(viewModelScope, SharingStarted.Lazily, oldFilteredSpells)

    init {
        spellsApi.getSpells {
            if (it == null) {
                _allSpellsState.value = AllSpellsState.ERROR
            } else {
                oldFilteredSpells = it
                _allSpells.value = it
                _allSpellsState.value = AllSpellsState.LOADED
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
