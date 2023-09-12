package com.till.paymentapplicationpoc.ui.state

/**
 * State representing purchase fragment
 */
sealed interface PurchaseState {
    data class Error(val message: String) : PurchaseState
    data class Success(val message: String): PurchaseState
    object Empty : PurchaseState
}