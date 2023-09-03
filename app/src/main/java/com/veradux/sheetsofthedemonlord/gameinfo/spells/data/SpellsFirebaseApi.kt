package com.veradux.sheetsofthedemonlord.gameinfo.spells.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.veradux.sheetsofthedemonlord.gameinfo.spells.model.Spell

class SpellsFirebaseApi : SpellsApi {

    override fun getSpells(onResultReceived: (Result<List<Spell>>) -> Unit) {
        val database = Firebase.database.reference

        val spellsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                onResultReceived(Result.success(dataSnapshot.getValue<List<Spell>>() ?: emptyList()))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onResultReceived(Result.failure(databaseError.toException()))
            }
        }

        // TODO check if this gets the newest spells when closing the screen, changing the spells in the db, and reopening the screen
        database.child("spells").addListenerForSingleValueEvent(spellsListener)
    }
}
