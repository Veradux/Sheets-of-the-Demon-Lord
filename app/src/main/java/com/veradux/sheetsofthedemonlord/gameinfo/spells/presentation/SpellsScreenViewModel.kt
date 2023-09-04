package com.veradux.sheetsofthedemonlord.gameinfo.spells.presentation

import androidx.lifecycle.ViewModel
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellFiltersRepository
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellFiltersRepositoryMock
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellsApi
import com.veradux.sheetsofthedemonlord.gameinfo.spells.data.SpellsFirebaseApi
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.SpellFilterCategories
import com.veradux.sheetsofthedemonlord.util.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SpellsScreenViewModel(
    spellsApi: SpellsApi = SpellsFirebaseApi(),
    spellFiltersRepository: SpellFiltersRepository = SpellFiltersRepositoryMock()
) : ViewModel() {

    private val _allSpells: MutableStateFlow<ResultState<List<Spell>>> = MutableStateFlow(ResultState.Loading())

    // TODO use the result state to show a loading animation or an error message
    val allSpells = _allSpells.asStateFlow()

    // states
    private val _isFilterDialogOpen = MutableStateFlow(false)
    val isFilterDialogOpen = _isFilterDialogOpen.asStateFlow()

    private val _filteredSpells = MutableStateFlow(emptyList<Spell>())
    val filteredSpells = _filteredSpells.asStateFlow()

    private val _spellFilters = MutableStateFlow(spellFiltersRepository.getSpellFilters())
    val spellFilters = _spellFilters.asStateFlow()

    private val _searchBarText = MutableStateFlow("")
    val searchBarText = _searchBarText.asStateFlow()

    init {
        spellsApi.getSpells {
            _allSpells.value = it
            if (it is ResultState.Received) {
                _filteredSpells.value = it.result
            }
        }
    }

    fun onSearchBarTextChange(text: String) {
        _searchBarText.value = text
        _filteredSpells.value = getFilteredSpells()
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

    fun setFilterDialogVisibilityStateTo(isVisible: Boolean) {
        _isFilterDialogOpen.value = isVisible
        if (!isVisible) {
            // TODO instead of always filtering the spells every time the screen closes,
            //  do it only when the spell filters change
            _filteredSpells.value = getFilteredSpells()
        }
    }

    private fun getFilteredSpells(): List<Spell> {
        return when (val spellsState = allSpells.value) {
            is ResultState.Received<List<Spell>> -> spellsState.result.filter { spell ->
                spell.containsText(searchBarText.value) && spellFilters.value.filter(spell)
            }
            else -> emptyList()
        }
    }
}
