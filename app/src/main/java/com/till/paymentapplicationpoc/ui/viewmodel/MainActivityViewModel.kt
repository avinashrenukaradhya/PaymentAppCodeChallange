package com.till.paymentapplicationpoc.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.till.paymentapplicationpoc.R
import com.till.paymentapplicationpoc.data.Transaction
import com.till.paymentapplicationpoc.di.ResourceProvider
import com.till.paymentapplicationpoc.domain.usecase.PurchaseUseCase
import com.till.paymentapplicationpoc.domain.usecase.RefundUseCase
import com.till.paymentapplicationpoc.domain.usecase.TransactionUseCase
import com.till.paymentapplicationpoc.ui.state.PurchaseState
import com.till.paymentapplicationpoc.ui.state.RefundState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel implementation for MainActivity class
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val purchaseUseCase: PurchaseUseCase,
    private val refundUseCase: RefundUseCase,
    private val transactionUseCase: TransactionUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _purchaseState : MutableStateFlow<PurchaseState> = MutableStateFlow(PurchaseState.Empty)
    val purchaseState = _purchaseState.asStateFlow()
    private val _refundState : MutableStateFlow<RefundState> = MutableStateFlow(RefundState.Empty)
    val refundState = _refundState.asStateFlow()

    /**
     * Initiate the purchase transaction
     * @param purchaseData
     */
    fun purchase(purchaseData: String) {
        _purchaseState.value = PurchaseState.Empty
        purchaseData.trim().toFloatOrNull()?.let { amount ->
            purchaseUseCase(amount)
            _purchaseState.value = PurchaseState.Success(resourceProvider.getString(R.string.success))
        } ?: run {
            _purchaseState.value = PurchaseState.Error(resourceProvider.getString(R.string.invalid_input))
        }
    }

    fun getTransactions(): List<Transaction> {
        return transactionUseCase()
    }

    /**
     * Initiate the refund transaction
     * @param refundData
     * @param transactionId
     */
    fun refund(refundData: String, transactionId: Long) {
        _refundState.value = RefundState.Empty
        refundData.trim().toFloatOrNull()?.let { refundAmount ->
            if (refundUseCase(refundAmount, transactionId)) {
                _refundState.value = RefundState.Success(resourceProvider.getString(R.string.success))
            } else {
                _refundState.value = RefundState.Error(resourceProvider.getString(R.string.refund_value_exceeded))
            }
        } ?: run {
            _refundState.value = RefundState.Error(resourceProvider.getString(R.string.invalid_input))
        }
    }
}