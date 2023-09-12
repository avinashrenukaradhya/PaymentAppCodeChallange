package com.till.paymentapplicationpoc.ui.state

/**
 * State representing Refund/Transaction fragment
 */
sealed interface RefundState {
    data class Error(val message: String) : RefundState
    data class Success(val message: String): RefundState
    object Empty : RefundState
}