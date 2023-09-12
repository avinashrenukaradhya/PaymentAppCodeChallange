package com.till.paymentapplicationpoc.domain.usecase

import com.till.paymentapplicationpoc.domain.TransactionRepository
import javax.inject.Inject

/**
 * UseCase for transactions
 */
class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    operator fun invoke() = transactionRepository.getTransactions()
}