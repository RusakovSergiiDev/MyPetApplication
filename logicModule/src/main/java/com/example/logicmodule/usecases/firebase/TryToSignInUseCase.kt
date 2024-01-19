package com.example.logicmodule.usecases.firebase

import com.example.logicmodule.FirebaseRepository
import javax.inject.Inject

class TryToSignInUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    fun execute(email: String, password: String, callback: (Boolean) -> Unit) {
        firebaseRepository.trySignIn(email, password, callback)
    }
}