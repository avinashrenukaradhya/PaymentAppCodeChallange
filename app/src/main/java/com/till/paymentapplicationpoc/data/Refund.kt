package com.till.paymentapplicationpoc.data

class Refund(
    override val id: Long,
    override val amount: Float,
    val referenceId: Long
): Transaction(id, amount)