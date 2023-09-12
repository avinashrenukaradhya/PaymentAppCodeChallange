package com.till.paymentapplicationpoc.data.repository

import com.till.paymentapplicationpoc.data.Purchase
import com.till.paymentapplicationpoc.data.Refund
import com.till.paymentapplicationpoc.data.Transaction

class TransactionService {
    private val transactions = mutableMapOf<Long, Transaction>()

    companion object {
        val instance = TransactionService()
    }

    fun pay(purchase: Purchase) {
        transactions[purchase.id] = purchase
    }

    fun getTransactions(): List<Transaction> = transactions.values.toList()

    fun getTransaction(id: Long): Transaction? = transactions[id]

    /**
     * To refund transaction
     * @param refund
     * @return success
     */
    fun refund(refund: Refund) : Boolean {
        val purchase = transactions[refund.referenceId] as Purchase
        if (refund.amount <= purchase.amount) { // check if the refund amount is greater then payment amount
            val refundTotal = purchase.refunds.map {
                it.amount
            }.sum() // calculate total of all the refund transactions
            if (refundTotal+refund.amount <= purchase.amount) { // check if current refund and total of previous refunds exceed payment amount
                transactions[refund.id] = refund
                purchase.refunds.add(refund)
                return true
            }
        }
        return false
    }
}
