package com.till.paymentapplicationpoc.ui.adapter

import com.till.paymentapplicationpoc.data.Transaction
import dagger.assisted.AssistedFactory

@AssistedFactory
interface TransactionAdapterFactory {
    fun create(
        dataSet: List<Transaction>,
        onClickListener: TransactionsAdapter.TransactionClickListener
    ) : TransactionsAdapter
}