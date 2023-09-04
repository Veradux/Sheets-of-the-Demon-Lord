package com.veradux.sheetsofthedemonlord.util

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

inline fun <reified Resource> addFirebaseListener(
    path: String,
    crossinline onResult: (ResultState<Resource>) -> Unit
) {
    onResult(ResultState.Loading())
    val database = Firebase.database.reference

    val spellsListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val result = dataSnapshot.getValue<Resource>()
            if (result != null) {
                onResult(ResultState.Received(result))
            } else {
                onResult(ResultState.Empty())
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            onResult(ResultState.Failed(databaseError.toException()))
        }
    }

    database.child(path).addListenerForSingleValueEvent(spellsListener)
}
