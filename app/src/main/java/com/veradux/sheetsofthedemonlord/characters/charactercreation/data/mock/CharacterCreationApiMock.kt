package com.veradux.sheetsofthedemonlord.characters.charactercreation.data.mock

import com.veradux.sheetsofthedemonlord.characters.charactercreation.data.CharacterCreationApi
import com.veradux.sheetsofthedemonlord.characters.charactercreation.model.Ancestry

class CharacterCreationApiMock : CharacterCreationApi {

    // TODO instead of having all game info loaded in the app,
    //  use this api to upload the ancestries to firebase and check how it looks as a json.
    //  Then once it's good, remove these objects and read them from firebase instead.
    override fun getAncestries(): List<Ancestry> {
        return listOf(human, goblin)
    }

}
