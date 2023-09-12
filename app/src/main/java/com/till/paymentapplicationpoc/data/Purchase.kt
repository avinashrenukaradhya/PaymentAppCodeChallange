package com.till.paymentapplicationpoc.data

class Purchase(
    override val id: Long,
    override val amount: Float,
    val refunds: MutableList<Refund>
): Transaction(id, amount)