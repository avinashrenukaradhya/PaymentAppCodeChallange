package com.till.paymentapplicationpoc.data.repository

import com.till.paymentapplicationpoc.data.Purchase
import com.till.paymentapplicationpoc.data.Refund
import com.till.paymentapplicationpoc.data.Transaction
import com.till.paymentapplicationpoc.domain.TransactionRepository
import javax.inject.Inject

/**
 * Repository implementation for communication with transaction
 */
class TransactionRepositoryImp @Inject constructor(
    private val transactionService: TransactionService
): TransactionRepository {
    override fun purchase(purchase: Purchase) = transactionService.pay(purchase)

    override fun refund(refund: Refund): Boolean = transactionService.refund(refund)

    override fun getTransactions(): List<Transaction> = transactionService.getTransactions()
}