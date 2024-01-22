package com.example.logicmodule.repository

import javax.inject.Inject

class EnglishGameRepository  @Inject constructor(
    private val contentRepository: ContentRepository,
    private val firebaseRepository: FirebaseRepository,
) {

    companion object {
        private const val LOG_TAG = "ENGLISH_GAME_REPOSITORY"
    }

}