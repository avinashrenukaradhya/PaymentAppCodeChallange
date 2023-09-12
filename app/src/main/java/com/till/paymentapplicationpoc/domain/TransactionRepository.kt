package com.till.paymentapplicationpoc.domain

import com.till.paymentapplicationpoc.data.Purchase
import com.till.paymentapplicationpoc.data.Refund
import com.till.paymentapplicationpoc.data.Transaction

/**
 * Repository interface for communication with transaction
 */
interface TransactionRepository {
    fun purchase(purchase: Purchase)
    fun refund(refund: Refund) : Boolean
    fun getTransactions() : List<Transaction>
}