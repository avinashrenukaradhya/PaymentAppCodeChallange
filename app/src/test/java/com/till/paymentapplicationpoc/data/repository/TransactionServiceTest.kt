package com.till.paymentapplicationpoc.data.repository

import com.till.paymentapplicationpoc.data.Purchase
import com.till.paymentapplicationpoc.data.Refund
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TransactionServiceTest {

    private lateinit var subject : TransactionService

    @Before
    fun setUp() {
        subject = TransactionService.instance
    }

    @Test
    fun `test purchase` () {
        val transactionId = System.currentTimeMillis()
        val amount = 10.0f

        subject.pay(Purchase(transactionId, amount, mutableListOf()))

        val transaction = subject.getTransactions().find { transaction ->
            transaction.id == transactionId
        }
        Assert.assertEquals(amount, transaction?.amount)
    }

    @Test
    fun `test refund` () {
        val paymentTransactionId = System.currentTimeMillis()
        val amount = 10.0f
        subject.pay(Purchase(paymentTransactionId, amount, mutableListOf()))
        val refundTransactionId = System.currentTimeMillis()

        subject.refund(Refund(refundTransactionId, 10.0f, paymentTransactionId))

        val transaction = subject.getTransactions().find { transaction ->
            transaction.id == refundTransactionId
        }
        Assert.assertEquals(amount, transaction?.amount)
    }

    @Test
    fun `refund request with excess amount should not be recorded` () {
        val paymentTransactionId = System.currentTimeMillis()
        val amount = 10.0f
        subject.pay(Purchase(paymentTransactionId, amount, mutableListOf()))
        val refundTransactionId = System.currentTimeMillis()+1

        subject.refund(Refund(refundTransactionId, 10.1f, paymentTransactionId))

        val transaction = subject.getTransactions().find { transaction ->
            transaction.id == refundTransactionId
        }
        Assert.assertNull(transaction)
    }

    @Test
    fun `refund request with excess amount return false` () {
        val paymentTransactionId = System.currentTimeMillis()
        val amount = 10.0f
        subject.pay(Purchase(paymentTransactionId, amount, mutableListOf()))
        val refundTransactionId = System.currentTimeMillis()

        val refundStatus = subject.refund(Refund(refundTransactionId, 10.1f, paymentTransactionId))

        Assert.assertFalse(refundStatus)
    }

    @Test
    fun `test to check part refund` () {
        val paymentTransactionId = System.currentTimeMillis()
        val amount = 10.0f
        subject.pay(Purchase(paymentTransactionId, amount, mutableListOf()))
        val firstRefundTransactionId = System.currentTimeMillis() + 1 // to avoid ID conflicts
        val secondRefundTransactionId = System.currentTimeMillis() + 2 // to avoid ID conflicts
        subject.refund(Refund(firstRefundTransactionId, 10.0f, paymentTransactionId))

        val refundStatus = subject.refund(Refund(secondRefundTransactionId, 0.1f, paymentTransactionId))

        Assert.assertFalse(refundStatus)
    }
}