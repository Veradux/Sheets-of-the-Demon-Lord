package com.veradux.sheetsofthedemonlord.util

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

inline fun <reified T> addFirebaseListener(path: String, crossinline onResult: (T?) -> Unit) {
    Firebase.database.reference.child(path).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val result = dataSnapshot.getValue<T>()
            if (result == null) {
                Firebase.analytics.logEvent(
                    "Could not find data for \"$path\" of type \"${T::class.java.simpleName}\"",
                    null
                )
            }
            onResult(result)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            onResult(null)
            Firebase.analytics.logEvent(databaseError.toException().stackTraceToString(), null)
        }
    })
}
