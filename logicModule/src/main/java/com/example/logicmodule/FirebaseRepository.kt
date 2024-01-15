package com.example.logicmodule

import com.example.datamodule.dto.EnglishIrregularVerbDto
import com.example.datamodule.dto.SpanishVerbDto
import com.example.datamodule.mapper.mapSpanishVerbModel
import com.example.datamodule.mapper.mapToEnglishIrregularVerbModel
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
    private val spanishTop200VerbsReference =
        database.getReference(SPANISH_TOP_200_VERBS_PATH)

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
    val englishIrregularVerbsFlow: Flow<List<EnglishIrregularVerbModel>> =
        englishIrregularVerbsTaskSourceFlow.map {
            if (it is Task.Success) it.data
            else emptyList()
        }
    val spanishTop200VerbsFlow: Flow<List<SpanishVerbModel>> =
        spanishTop200VerbsTaskSourceFlow.map {
            if (it is Task.Success) it.data
            else emptyList()
        }

    fun getCurrentUser() = firebaseAuth.currentUser

    fun trySignIn(email: String, password: String, callback: (Boolean) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.invoke(true)
                } else {
                    callback.invoke(false)
                }
            }
    }

    fun getEnglishIrregularVerbsFlowOrLoad(): Flow<List<EnglishIrregularVerbModel>> {
        if (englishIrregularVerbsSourceFlow.value.isEmpty()) {
            loadEnglishIrregularVerbs()
        }
        return englishIrregularVerbsFlow
    }

    fun getEnglishIrregularVerbsTaskFlowOrLoad(): Flow<Task<List<EnglishIrregularVerbModel>>> {
        if (englishIrregularVerbsSourceFlow.value.isEmpty()) {
            loadEnglishIrregularVerbs()
        }
        return englishIrregularVerbsTaskSourceFlow
    }

    fun getSpanishTop200VerbsTaskFlowOrLoad(): Flow<Task<List<SpanishVerbModel>>> {
        if (spanishTop200VerbsSourceFlow.value.isEmpty()) {
            loadSpanishVerbs()
        }
        return spanishTop200VerbsTaskSourceFlow
    }

    private fun loadEnglishIrregularVerbs() {
        englishIrregularVerbsTaskSourceFlow.value = Task.Loading
        englishAllIrregularVerbsReference.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    val result =
                        children.mapNotNull { it.getValue<EnglishIrregularVerbDto>() }
                    englishIrregularVerbsSourceFlow.value = result
                    englishIrregularVerbsTaskSourceFlow.value =
                        Task.Success(result.mapToEnglishIrregularVerbModel())
                } else {
                    englishIrregularVerbsTaskSourceFlow.value = Task.Empty
                }
            }

            override fun onCancelled(error: DatabaseError) {
                englishIrregularVerbsTaskSourceFlow.value = Task.Error("error")
            }
        })
    }

    private fun loadSpanishVerbs() {
        spanishTop200VerbsTaskSourceFlow.value = Task.Loading
        spanishTop200VerbsReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    val result =
                        children.mapNotNull { it.getValue<SpanishVerbDto>() }
                    spanishTop200VerbsSourceFlow.value = result
                    spanishTop200VerbsTaskSourceFlow.value =
                        Task.Success(result.mapSpanishVerbModel())
                } else {
                    spanishTop200VerbsTaskSourceFlow.value = Task.Empty
                }
            }

            override fun onCancelled(error: DatabaseError) {
                spanishTop200VerbsTaskSourceFlow.value = Task.Error("error")
            }
        })
    }
}