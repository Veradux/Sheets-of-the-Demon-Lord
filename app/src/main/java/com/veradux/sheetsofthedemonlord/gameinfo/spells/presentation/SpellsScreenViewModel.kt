package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.lifecycle.ViewModel
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellFiltersApi
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellFiltersApiMock
import androidx.lifecycle.viewModelScope
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellFiltersRepository
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellFiltersRepositoryMock
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellsApi
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellsFirebaseApi
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.SpellFilterCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn

class SpellsScreenViewModel(
    spellsApi: SpellsApi = SpellsFirebaseApi(),
    spellFiltersApi: SpellFiltersApi = SpellFiltersApiMock()
) : ViewModel() {

    enum class AllSpellsState {
        LOADING, LOADED, ERROR
    }

    private val _allSpellsState = MutableStateFlow(AllSpellsState.LOADING)
    val allSpellsState = _allSpellsState.asStateFlow()

    private val _allSpells = MutableStateFlow(emptyList<Spell>())

    private val _isFilterDialogOpen = MutableStateFlow(false)
    val isFilterDialogOpen = _isFilterDialogOpen.asStateFlow()

    private val _spellFilters = MutableStateFlow(spellFiltersApi.getSpellFilters())
    val spellFilters = _spellFilters.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _filteredSpells = MutableStateFlow(emptyList<Spell>())
    val filteredSpells = _searchQuery
        // TODO check if filtering the search query still works when spellFilters changes.
        .filter { it.length < 3 }
        .combine(spellFilters) { query, filters ->
            _allSpells.value.filter { spell ->
                spell.containsText(query) && filters.filter(spell)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, _allSpells.value)

    init {
        spellsApi.getSpells {
            if (it == null) {
                _allSpellsState.value = AllSpellsState.ERROR
            } else {
                _filteredSpells.value = it
                _allSpells.value = it
                _allSpellsState.value = AllSpellsState.LOADED
            }
        }
    }

    fun onSearchQueryChange(text: String) {
        _searchQuery.value = text
    }

    fun updateSpellFilters(
        filters: List<SpellFilterCategories.Filter>,
        newFilter: SpellFilterCategories.Filter,
        copyFilters: (List<SpellFilterCategories.Filter>) -> SpellFilterCategories
    ) {
        val mutableToggles = filters.toMutableList()
        mutableToggles[mutableToggles.indexOf(newFilter)] =
            SpellFilterCategories.Filter(newFilter.name, !newFilter.isEnabled)
        _spellFilters.value = copyFilters(mutableToggles)
    }

    fun setFilterDialogVisibilityTo(isVisible: Boolean) {
        _isFilterDialogOpen.value = isVisible
    }
}
