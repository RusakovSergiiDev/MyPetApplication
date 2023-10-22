package com.example.mypetapplication.features.english

import androidx.lifecycle.asLiveData
import com.example.mypetapplication.base.BaseViewModel
import com.example.mypetapplication.data.EnglishIrregularVerbDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

class EnglishAllIrregularVerbsViewModel : BaseViewModel() {

    // Internal param(s)
    private val database = Firebase.database
    private val reference = database.getReference("source/english/englishAllIrregularVerbs")
    private val englishIrregularVerbsSourceFlow =
        MutableStateFlow<List<EnglishIrregularVerbDto>>(emptyList())

    // External param(s)
    val englishIrregularVerbsLiveData = englishIrregularVerbsSourceFlow.asLiveData()

    init {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    val englishIrregularVerbs =
                        children.mapNotNull { it.getValue<EnglishIrregularVerbDto>() }
                    englishIrregularVerbsSourceFlow.value = englishIrregularVerbs
                } else {
                    // Handle the case where no data exists
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
            }
        })
    }
}