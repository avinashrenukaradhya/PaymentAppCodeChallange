package com.till.paymentapplicationpoc.ui.viewmodel

import com.till.paymentapplicationpoc.di.ResourceProvider
import com.till.paymentapplicationpoc.domain.usecase.PurchaseUseCase
import com.till.paymentapplicationpoc.domain.usecase.RefundUseCase
import com.till.paymentapplicationpoc.domain.usecase.TransactionUseCase
import com.till.paymentapplicationpoc.ui.state.PurchaseState
import com.till.paymentapplicationpoc.ui.state.RefundState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class MainActivityViewModelTest {
    private lateinit var subject: MainActivityViewModel
    private val purchaseUseCase: PurchaseUseCase = mock()
    private val refundUseCase: RefundUseCase = mock()
    private val transactionUseCase: TransactionUseCase = mock()
    private val resourceProvider: ResourceProvider = mock()

    @Before
    fun setUp() {
        subject = MainActivityViewModel(purchaseUseCase, refundUseCase, transactionUseCase, resourceProvider)
    }

    @Test
    fun `test pay`() = runBlocking {
        doNothing().`when`(purchaseUseCase).invoke(any())
        whenever(resourceProvider.getString(any())).thenReturn("Success")
        subject.purchase("10")

        val state = subject.purchaseState.first()

        Assert.assertTrue(state is PurchaseState.Success)
    }

    @Test
    fun `test pay empty input`() = runBlocking {
        whenever(resourceProvider.getString(any())).thenReturn("Fail")
        subject.purchase("")

        val state = subject.purchaseState.first()

        Assert.assertTrue(state is PurchaseState.Error)
    }

    @Test
    fun `test refund`() = runBlocking {
        whenever(refundUseCase(any(), any())).thenReturn(true)
        whenever(resourceProvider.getString(any())).thenReturn("Success")
        subject.refund("10", 1L)

        val state = subject.refundState.first()

        Assert.assertTrue(state is RefundState.Success)
    }

    @Test
    fun `test refund un success`() = runBlocking {
        whenever(refundUseCase(any(), any())).thenReturn(false)
        whenever(resourceProvider.getString(any())).thenReturn("Fail")
        subject.refund("10", 1L)

        val state = subject.refundState.first()

        Assert.assertTrue(state is RefundState.Error)
    }

    @Test
    fun `test refund empty input`() = runBlocking {
        whenever(resourceProvider.getString(any())).thenReturn("Fail")
        subject.refund("", 1L)

        val state = subject.refundState.first()

        Assert.assertTrue(state is RefundState.Error)
    }
}