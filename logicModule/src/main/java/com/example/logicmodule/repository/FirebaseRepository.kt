package com.example.logicmodule.repository

import android.util.Log
import com.example.datamodule.dto.EnglishIrregularVerbDto
import com.example.datamodule.dto.EnglishItVerbDto
import com.example.datamodule.dto.SpanishVerbDto
import com.example.datamodule.dto.server.EnglishRulesDto
import com.example.datamodule.mapper.mapSpanishVerbModel
import com.example.datamodule.mapper.mapToEnglishIrregularVerbModel
import com.example.datamodule.mapper.mapToEnglishItVerbModel
import com.example.datamodule.mapper.mapToEnglishRulesModel
import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.models.SpanishVerbModel
import com.example.datamodule.models.english.EnglishItVerbModel
import com.example.datamodule.models.english.EnglishRulesModel
import com.example.datamodule.types.Task
import com.example.datamodule.types.isInitial
import com.example.datamodule.types.isSuccess
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    companion object {
        private const val LOG_TAG = "FirebaseLogTag"
        private const val ENGLISH_RULES_PATH = "source/english/englishRules"
        private const val ENGLISH_IT_VERBS_PATH = "source/english/englishItVerbs"
        private const val ENGLISH_IRREGULAR_VERBS_PATH = "source/english/englishAllIrregularVerbs"
        private const val SPANISH_TOP_200_VERBS_PATH = "source/spanish/verbs/top200"
    }

    // Firebase param(s)
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database
    private val englishRulesReference =
        database.getReference(ENGLISH_RULES_PATH)
    private val englishItVerbsReference =
        database.getReference(ENGLISH_IT_VERBS_PATH)
    private val englishAllIrregularVerbsReference =
        database.getReference(ENGLISH_IRREGULAR_VERBS_PATH)
    private val spanishTop200VerbsReference =
        database.getReference(SPANISH_TOP_200_VERBS_PATH)

    // Storage param(s)
    private val englishItVerbs = mutableListOf<EnglishItVerbModel>()

    // Internal param(s)
    private val englishRulesFlowSource =
        MutableStateFlow<Task<EnglishRulesModel>>(Task.Initial)
    private val englishItVerbsFlowSource =
        MutableStateFlow<Task<List<EnglishItVerbModel>>>(Task.Initial)
    private val englishIrregularVerbsTaskFlowSource =
        MutableStateFlow<Task<List<EnglishIrregularVerbModel>>>(Task.Initial)
    private val spanishTop200VerbsTaskFlowSource =
        MutableStateFlow<Task<List<SpanishVerbModel>>>(Task.Initial)

    fun getCurrentUser() = firebaseAuth.currentUser

    fun trySignInAsync(email: String, password: String): Deferred<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password).asDeferred()
    }

    fun tryLogOut() {
        return firebaseAuth.signOut()
    }

    @SuppressWarnings("MagicNumber")
    suspend fun getEnglishRulesTaskFlowOrLoad(): Flow<Task<EnglishRulesModel>> {
        if (englishRulesFlowSource.value.isInitial()) {
            coroutineScope {
                launch {
                    delay(1000)
                    loadEnglishRules()
                }
            }
        }
        return englishRulesFlowSource
    }

    suspend fun getEnglishItVerbsTaskFlowOrLoad(): Flow<Task<List<EnglishItVerbModel>>> {
        if (englishItVerbs.isEmpty()) {
            withContext(Dispatchers.IO) {
                loadEnglishItVerbs()
            }
        }
        return englishItVerbsFlowSource
    }

    suspend fun getEnglishIrregularVerbsTaskFlowOrLoad(): Flow<Task<List<EnglishIrregularVerbModel>>> {
        if (englishIrregularVerbsTaskFlowSource.value.isInitial()) {
            coroutineScope {
                launch {
                    loadEnglishIrregularVerbs()
                }
            }
        }
        return englishIrregularVerbsTaskFlowSource
    }

    suspend fun getSpanishTop200VerbsTaskFlowOrLoad(): Flow<Task<List<SpanishVerbModel>>> {
        if (spanishTop200VerbsTaskFlowSource.value.isInitial()) {
            coroutineScope {
                launch {
                    loadSpanishVerbs()
                }
            }
        }
        return spanishTop200VerbsTaskFlowSource
    }

    fun loadEnglishRules() {
        loadDataFromFirebase<EnglishRulesDto, EnglishRulesModel>(
            reference = englishRulesReference,
            mapper = { it.mapToEnglishRulesModel() },
            callback = { task ->
                processCallbackEvent(
                    loadingTaskFlow = englishRulesFlowSource,
                    task = task
                )
            }
        )
    }

    @SuppressWarnings("MagicNumber")
    suspend fun loadEnglishItVerbs() {
        delay(1000)
        loadListDataFromFirebase<EnglishItVerbDto, EnglishItVerbModel>(
            reference = englishItVerbsReference,
            mapper = { it.mapToEnglishItVerbModel() },
            callback = { task ->
                if (task is Task.Success) {
                    val data = task.data
                    englishItVerbs.clear()
                    englishItVerbs.addAll(data)
                }
                processListCallbackForSharedFlowEvent(
                    loadingTaskFlow = englishItVerbsFlowSource,
                    task = task
                )
            }
        )
    }

    fun loadEnglishIrregularVerbs() {
        loadListDataFromFirebase<EnglishIrregularVerbDto, EnglishIrregularVerbModel>(
            reference = englishAllIrregularVerbsReference,
            mapper = { it.mapToEnglishIrregularVerbModel() },
            callback = { task ->
                processListCallbackEvent(
                    loadingTaskFlow = englishIrregularVerbsTaskFlowSource,
                    task = task
                )
            }
        )
    }

    fun loadSpanishVerbs() {
        loadListDataFromFirebase<SpanishVerbDto, SpanishVerbModel>(
            reference = spanishTop200VerbsReference,
            mapper = { it.mapSpanishVerbModel() },
            callback = { task ->
                processListCallbackEvent(
                    loadingTaskFlow = spanishTop200VerbsTaskFlowSource,
                    task = task
                )
            }
        )
    }

    private fun <T> processListCallbackEvent(
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

    private fun <T> processListCallbackForSharedFlowEvent(
        loadingTaskFlow: MutableSharedFlow<Task<List<T>>>,
        task: Task<List<T>>
    ) {
        val result = when (task) {
            is Task.Success -> Task.Success(task.data)
            is Task.Error -> Task.Error(task.errorMessage)
            is Task.Initial -> Task.Initial
            is Task.Loading -> Task.Loading
            is Task.Empty -> Task.Empty
        }
        loadingTaskFlow.tryEmit(result)
    }

    private fun <T> processCallbackEvent(
        loadingTaskFlow: MutableStateFlow<Task<T>>,
        task: Task<T>
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
        crossinline callback: (Task<A>) -> Unit
    ) {
        callback.invoke(Task.Loading)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val result = snapshot.getValue(T::class.java)
                    if (result == null) {
                        callback.invoke(Task.Error("null result"))
                        return
                    }
                    val mappedResult = mapper(result)
                    if (mappedResult != null) {
                        callback.invoke(Task.Success(mappedResult))
                    } else {
                        callback.invoke(Task.Error("null result"))
                    }
                } else {
                    callback.invoke(Task.Empty)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.invoke(Task.Error(error.message))
            }
        })
    }

    private inline fun <reified T, A> loadListDataFromFirebase(
        reference: DatabaseReference,
        crossinline mapper: (T) -> A?,
        crossinline callback: (Task<List<A>>) -> Unit
    ) {
        Log.d(LOG_TAG, "loadListDataFromFirebase: Task.Loading")
        callback.invoke(Task.Loading)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    val result = children.mapNotNull { it.getValue(T::class.java) }
                    val mappedResult = result.mapNotNull { mapper(it) }
                    Log.d(LOG_TAG, "loadListDataFromFirebase: Task.Success")
                    callback.invoke(Task.Success(mappedResult))
                } else {
                    Log.d(LOG_TAG, "loadListDataFromFirebase: Task.Empty")
                    callback.invoke(Task.Empty)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(LOG_TAG, "loadListDataFromFirebase: Task.Error")
                callback.invoke(Task.Error(error.message))
            }
        })
    }
}