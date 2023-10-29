package com.example.mypetapplication.repo

import com.example.datamodule.dto.EnglishIrregularVerbDto
import com.example.datamodule.dto.SpanishVerbDto
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.models.SpanishVerbModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

object FirebaseRepo {

    private const val ENGLISH_IRREGULAR_VERBS_PATH = "source/english/englishAllIrregularVerbs"
    private const val SPANISH_TOP_200_VERBS_PATH = "source/spanish/verbs/top200"

    // Firebase param(s)
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database
    private val englishAllIrregularVerbsReference =
        database.getReference(ENGLISH_IRREGULAR_VERBS_PATH)
    private val spanishTop200VerbsReference = database.getReference(SPANISH_TOP_200_VERBS_PATH)

    // Internal param(s)
    private val englishIrregularVerbsSourceFlow =
        MutableStateFlow<List<EnglishIrregularVerbDto>>(emptyList())
    private val spanishTop200VerbsSourceFlow =
        MutableStateFlow<List<SpanishVerbDto>>(emptyList())

    // External param(s)
    val englishIrregularVerbsFlow: Flow<List<EnglishIrregularVerbModel>> =
        englishIrregularVerbsSourceFlow.map { items ->
            items.mapNotNull {
                val infinitive = it.infinitive ?: return@mapNotNull null
                val simplePast = it.pastSimple ?: return@mapNotNull null
                val pastParticiple = it.pastParticiple ?: return@mapNotNull null
                val translateInUkrainian = it.translateInUkrainian ?: return@mapNotNull null
                val isPopular = it.isPopular ?: false
                EnglishIrregularVerbModel(
                    infinitive = infinitive,
                    pastSimple = simplePast,
                    pastParticiple = pastParticiple,
                    translateInUkrainian = translateInUkrainian,
                    isPopular = isPopular
                )
            }
        }
    val spanishTop200VerbsFlow: Flow<List<SpanishVerbModel>> =
        spanishTop200VerbsSourceFlow.map { items ->
            items.mapNotNull {
                val english = it.en ?: return@mapNotNull null
                val spanish = it.es ?: return@mapNotNull null
                val ukrainian = it.ua ?: return@mapNotNull null
                SpanishVerbModel(
                    english = english,
                    spanish = spanish,
                    ukrainian = ukrainian
                )
            }
        }

    fun getCurrentUser() = firebaseAuth.currentUser

    fun loadEnglishIrregularVerbs(statusCallback: (LoadStatus) -> Unit) {
        statusCallback.invoke(LoadStatus.Loading)
        englishAllIrregularVerbsReference.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    val result =
                        children.mapNotNull { it.getValue<EnglishIrregularVerbDto>() }
                    englishIrregularVerbsSourceFlow.value = result
                    statusCallback.invoke(LoadStatus.Success)
                } else {
                    statusCallback.invoke(LoadStatus.Empty)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                statusCallback.invoke(LoadStatus.Error)
            }
        })
    }

    fun loadSpanishVerbs(statusCallback: (LoadStatus) -> Unit) {
        statusCallback.invoke(LoadStatus.Loading)
        spanishTop200VerbsReference.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    val result =
                        children.mapNotNull { it.getValue<SpanishVerbDto>() }
                    spanishTop200VerbsSourceFlow.value = result
                    statusCallback.invoke(LoadStatus.Success)
                } else {
                    statusCallback.invoke(LoadStatus.Empty)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                statusCallback.invoke(LoadStatus.Error)
            }
        })
    }
}