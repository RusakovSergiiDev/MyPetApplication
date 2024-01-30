package com.example.logicmodule.usecases.firebase

import com.example.datamodule.types.Task
import com.example.logicmodule.repository.FirebaseRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class TryToLogOutCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    @SuppressWarnings("MagicNumber")
    suspend fun execute(callback: (Task<Unit>) -> Unit) {
        callback.invoke(Task.Loading)
        delay(2000)
        firebaseRepository.tryLogOut()
        callback.invoke(Task.Success(Unit))
    }
}