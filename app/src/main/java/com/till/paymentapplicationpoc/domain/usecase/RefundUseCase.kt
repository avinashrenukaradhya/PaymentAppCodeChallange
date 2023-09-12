package com.till.paymentapplicationpoc.domain.usecase

import com.till.paymentapplicationpoc.data.Refund
import com.till.paymentapplicationpoc.domain.TransactionRepository
import javax.inject.Inject

/**
 * UseCase for Refund transaction
 */
class RefundUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    operator fun invoke(refundAmount: Float, transactionId: Long): Boolean  {
        val refund = Refund(
            id = System.currentTimeMillis(),
            amount = refundAmount,
            referenceId = transactionId
        )
        return transactionRepository.refund(refund)
    }
}