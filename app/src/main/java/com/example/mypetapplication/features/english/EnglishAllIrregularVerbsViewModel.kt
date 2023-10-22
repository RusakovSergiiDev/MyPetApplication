package com.example.mypetapplication.features.english

import androidx.lifecycle.asLiveData
import com.example.datamodule.dto.EnglishIrregularVerbDto
import com.example.mypetapplication.base.BaseViewModel
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.mypetapplication.ui.data.EnglishIrregularVerbUiItem
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
        MutableStateFlow<List<EnglishIrregularVerbModel>>(emptyList())

    // External param(s)
    val englishIrregularVerbsLiveData = englishIrregularVerbsSourceFlow.asLiveData()

    init {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    val result =
                        children.mapNotNull { it.getValue<EnglishIrregularVerbDto>() }
                    val englishIrregularVerbs = result.mapNotNull {
                        val infinitive = it.infinitive ?: return@mapNotNull null
                        val simplePast = it.pastSimple ?: return@mapNotNull null
                        val pastParticiple = it.pastParticiple ?: return@mapNotNull null
                        val translateInUkrainian = it.translateInUkrainian ?: return@mapNotNull null
                        EnglishIrregularVerbModel(
                            infinitive = infinitive,
                            pastSimple = simplePast,
                            pastParticiple = pastParticiple,
                            translateInUkrainian = translateInUkrainian
                        )
                    }
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