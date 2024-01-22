package com.example.logicmodule.usecases.firebase

import com.example.datamodule.types.Task
import com.example.logicmodule.repository.FirebaseRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class TryToSignInUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun execute(email: String, password: String, callback: (Task<Unit>) -> Unit) {
        callback.invoke(Task.Loading)
        try {
            delay(500)
            val authResult = firebaseRepository.trySignInAsync(email, password).await()
            if (authResult.user != null) {
                callback.invoke(Task.Success(Unit))
            } else {
                callback.invoke(Task.Error(""))
            }
        } catch (e: Exception) {
            callback.invoke(Task.Error(e.message ?: "empty message"))
        }
    }
}