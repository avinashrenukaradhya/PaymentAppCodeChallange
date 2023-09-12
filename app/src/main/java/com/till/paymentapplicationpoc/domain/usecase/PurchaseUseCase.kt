package com.till.paymentapplicationpoc.domain.usecase

import com.till.paymentapplicationpoc.data.Purchase
import com.till.paymentapplicationpoc.domain.TransactionRepository
import javax.inject.Inject

/**
 * UseCase for purchase transaction
 */
class PurchaseUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    operator fun invoke(amount: Float) = transactionRepository.purchase(Purchase(System.currentTimeMillis(), amount, mutableListOf()))
}