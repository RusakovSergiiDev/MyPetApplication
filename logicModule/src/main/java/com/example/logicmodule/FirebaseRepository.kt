package com.example.logicmodule

import com.example.datamodule.dto.EnglishIrregularVerbDto
import com.example.datamodule.dto.SpanishVerbDto
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.models.SpanishVerbModel
import com.example.datamodule.types.LoadStatus
import com.example.datamodule.types.Task
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
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    companion object {
        private const val ENGLISH_IRREGULAR_VERBS_PATH = "source/english/englishAllIrregularVerbs"
        private const val SPANISH_TOP_200_VERBS_PATH = "source/spanish/verbs/top200"
    }

    // Firebase param(s)
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database
    private val englishAllIrregularVerbsReference =
        database.getReference(ENGLISH_IRREGULAR_VERBS_PATH)
    private val spanishTop200VerbsReference = database.getReference(SPANISH_TOP_200_VERBS_PATH)

    // Internal param(s)
    private val englishIrregularVerbsTaskSourceFlow =
        MutableStateFlow<Task<List<EnglishIrregularVerbModel>>>(Task.Initial)
    private val englishIrregularVerbsSourceFlow =
        MutableStateFlow<List<EnglishIrregularVerbDto>>(emptyList())
    private val spanishTop200VerbsTaskSourceFlow =
        MutableStateFlow<Task<List<SpanishVerbModel>>>(Task.Initial)
    private val spanishTop200VerbsSourceFlow =
        MutableStateFlow<List<SpanishVerbDto>>(emptyList())

    // External param(s)
    val englishIrregularVerbsTaskFlow: Flow<Task<List<EnglishIrregularVerbModel>>> =
        englishIrregularVerbsTaskSourceFlow
    val englishIrregularVerbsFlow: Flow<List<EnglishIrregularVerbModel>> =
        englishIrregularVerbsTaskFlow.map {
            if (it is Task.Success) it.data
            else emptyList()
        }
    val spanishTop200VerbsTaskFlow: Flow<Task<List<SpanishVerbModel>>> =
        spanishTop200VerbsTaskSourceFlow
    val spanishTop200VerbsFlow: Flow<List<SpanishVerbModel>> =
        spanishTop200VerbsTaskFlow.map {
            if (it is Task.Success) it.data
            else emptyList()
        }

    private fun List<EnglishIrregularVerbDto>.mapToEnglishIrregularVerbModel(): List<EnglishIrregularVerbModel> {
        return this.mapNotNull { item ->
            val infinitive = item.infinitive ?: return@mapNotNull null
            val simplePast = item.pastSimple ?: return@mapNotNull null
            val pastParticiple = item.pastParticiple ?: return@mapNotNull null
            val translateInUkrainian = item.translateInUkrainian ?: return@mapNotNull null
            val isPopular = item.isPopular ?: false
            EnglishIrregularVerbModel(
                infinitive = infinitive,
                pastSimple = simplePast,
                pastParticiple = pastParticiple,
                translateInUkrainian = translateInUkrainian,
                isPopular = isPopular
            )
        }
    }

    private fun List<SpanishVerbDto>.mapSpanishVerbModel(): List<SpanishVerbModel> {
        return this.mapNotNull { item ->
            val english = item.en ?: return@mapNotNull null
            val spanish = item.es ?: return@mapNotNull null
            val ukrainian = item.ua ?: return@mapNotNull null
            SpanishVerbModel(
                english = english,
                spanish = spanish,
                ukrainian = ukrainian
            )
        }
    }

    fun getCurrentUser() = firebaseAuth.currentUser

    fun getEnglishIrregularVerbsFlowOrLoad(statusCallback: ((LoadStatus) -> Unit)? = null): Flow<List<EnglishIrregularVerbModel>> {
        if (englishIrregularVerbsSourceFlow.value.isEmpty()) {
            loadEnglishIrregularVerbs(statusCallback)
        }
        return englishIrregularVerbsFlow
    }

    fun getEnglishIrregularVerbsTaskFlowOrLoad(): Flow<Task<List<EnglishIrregularVerbModel>>> {
        if (englishIrregularVerbsSourceFlow.value.isEmpty()) {
            loadEnglishIrregularVerbs()
        }
        return englishIrregularVerbsTaskFlow
    }

    fun getSpanishTop200VerbsTaskFlowOrLoad(): Flow<Task<List<SpanishVerbModel>>> {
        if (englishIrregularVerbsSourceFlow.value.isEmpty()) {
            loadEnglishIrregularVerbs()
        }
        return spanishTop200VerbsTaskFlow
    }

    fun loadEnglishIrregularVerbs(statusCallback: ((LoadStatus) -> Unit)? = null) {
        statusCallback?.invoke(LoadStatus.Loading)
        englishIrregularVerbsTaskSourceFlow.value = Task.Loading
        englishAllIrregularVerbsReference.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    val result =
                        children.mapNotNull { it.getValue<EnglishIrregularVerbDto>() }
                    englishIrregularVerbsSourceFlow.value = result
                    statusCallback?.invoke(LoadStatus.Success)
                    englishIrregularVerbsTaskSourceFlow.value = Task.Success(result.mapToEnglishIrregularVerbModel())
                } else {
                    statusCallback?.invoke(LoadStatus.Empty)
                    englishIrregularVerbsTaskSourceFlow.value = Task.Empty
                }
            }

            override fun onCancelled(error: DatabaseError) {
                statusCallback?.invoke(LoadStatus.Error)
                englishIrregularVerbsTaskSourceFlow.value = Task.Error("error")
            }
        })
    }

    fun loadSpanishVerbs(statusCallback: (LoadStatus) -> Unit) {
        statusCallback.invoke(LoadStatus.Loading)
        spanishTop200VerbsTaskSourceFlow.value = Task.Loading
        spanishTop200VerbsReference.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    val result =
                        children.mapNotNull { it.getValue<SpanishVerbDto>() }
                    spanishTop200VerbsSourceFlow.value = result
                    statusCallback.invoke(LoadStatus.Success)
                    spanishTop200VerbsTaskSourceFlow.value = Task.Success(result.mapSpanishVerbModel())
                } else {
                    statusCallback.invoke(LoadStatus.Empty)
                    spanishTop200VerbsTaskSourceFlow.value = Task.Empty
                }
            }

            override fun onCancelled(error: DatabaseError) {
                statusCallback.invoke(LoadStatus.Error)
                spanishTop200VerbsTaskSourceFlow.value = Task.Error("error")
            }
        })
    }
}