package com.example.logicmodule.usecases.firebase

import com.example.logicmodule.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetFirebaseCurrentUserUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    fun execute(): FirebaseUser? {
        return firebaseRepository.getCurrentUser()
    }
}