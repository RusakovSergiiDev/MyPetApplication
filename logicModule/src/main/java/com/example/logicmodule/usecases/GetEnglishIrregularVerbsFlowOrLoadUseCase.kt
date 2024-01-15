package com.example.logicmodule.usecases

import com.example.datamodule.models.EnglishIrregularVerbModel
import com.example.datamodule.types.LoadStatus
import com.example.logicmodule.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEnglishIrregularVerbsFlowOrLoadUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    fun execute(statusCallback: ((LoadStatus) -> Unit)? = null): Flow<List<EnglishIrregularVerbModel>> {
        return firebaseRepository.getEnglishIrregularVerbsFlowOrLoad(statusCallback)
    }
}