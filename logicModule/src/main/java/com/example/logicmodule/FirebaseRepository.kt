package com.example.logicmodule

import com.example.datamodule.dto.EnglishIrregularVerbDto
import com.example.datamodule.dto.SpanishVerbDto
import com.example.datamodule.mapper.mapSpanishVerbModel
import com.example.datamodule.mapper.mapToEnglishIrregularVerbModel
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.models.SpanishVerbModel
import com.example.datamodule.types.Task
import com.example.datamodule.types.isInitial
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    companion object {
        private const val LOG_TAG = "FirebaseLogTag"
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
    private val spanishTop200VerbsTaskSourceFlow =
        MutableStateFlow<Task<List<SpanishVerbModel>>>(Task.Initial)

    fun getCurrentUser() = firebaseAuth.currentUser

    fun trySignIn(email: String, password: String, callback: (Boolean) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback.invoke(task.isSuccessful)
            }
    }

    fun getEnglishIrregularVerbsTaskFlowOrLoad(): Flow<Task<List<EnglishIrregularVerbModel>>> {
        if (englishIrregularVerbsTaskSourceFlow.value.isInitial()) {
            loadEnglishIrregularVerbs()
        }
        return englishIrregularVerbsTaskSourceFlow
    }

    fun getSpanishTop200VerbsTaskFlowOrLoad(): Flow<Task<List<SpanishVerbModel>>> {
        if (spanishTop200VerbsTaskSourceFlow.value.isInitial()) {
            loadSpanishVerbs()
        }
        return spanishTop200VerbsTaskSourceFlow
    }

    fun loadEnglishIrregularVerbs() {
        loadDataFromFirebase<EnglishIrregularVerbDto, EnglishIrregularVerbModel>(
            reference = englishAllIrregularVerbsReference,
            mapper = { it.mapToEnglishIrregularVerbModel() },
            callback = { task ->
                processCallbackEvent(
                    loadingTaskFlow = englishIrregularVerbsTaskSourceFlow,
                    task = task
                )
            }
        )
    }

    fun loadSpanishVerbs() {
        loadDataFromFirebase<SpanishVerbDto, SpanishVerbModel>(
            reference = spanishTop200VerbsReference,
            mapper = { it.mapSpanishVerbModel() },
            callback = { task ->
                processCallbackEvent(
                    loadingTaskFlow = spanishTop200VerbsTaskSourceFlow,
                    task = task
                )
            }
        )
    }

    private fun <T> processCallbackEvent(
        loadingTaskFlow: MutableStateFlow<Task<List<T>>>,
        task: Task<List<T>>
    ) {
        val result = when (task) {
            is Task.Success -> Task.Success(task.data)
            is Task.Error -> Task.Error(task.errorMessage)
            is Task.Initial -> Task.Initial
            is Task.Loading -> Task.Loading
            is Task.Empty -> Task.Empty
        }
        loadingTaskFlow.value = result
    }

    private inline fun <reified T, A> loadDataFromFirebase(
        reference: DatabaseReference,
        crossinline mapper: (T) -> A?,
        crossinline callback: (Task<List<A>>) -> Unit
    ) {
        callback.invoke(Task.Loading)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    val result =
                        children.mapNotNull { it.getValue(T::class.java) }
                    val mappedResult = result.mapNotNull { mapper(it) }
                    callback.invoke(Task.Success(mappedResult))
                } else {
                    callback.invoke(Task.Empty)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.invoke(Task.Error(error.message))
            }
        })
    }
}