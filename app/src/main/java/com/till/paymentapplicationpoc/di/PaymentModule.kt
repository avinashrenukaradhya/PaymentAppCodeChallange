package com.till.paymentapplicationpoc.di

import android.content.Context
import com.till.paymentapplicationpoc.data.repository.TransactionRepositoryImp
import com.till.paymentapplicationpoc.domain.usecase.PurchaseUseCase
import com.till.paymentapplicationpoc.data.repository.TransactionService
import com.till.paymentapplicationpoc.domain.TransactionRepository
import com.till.paymentapplicationpoc.domain.usecase.RefundUseCase
import com.till.paymentapplicationpoc.domain.usecase.TransactionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module class of providers used in the project
 */
@Module
@InstallIn(SingletonComponent::class)
class PaymentModule {

    @Provides
    @Singleton
    fun provideTransactionService(): TransactionService {
        return TransactionService.instance
    }

    @Provides
    @Singleton
    fun providePurchaseUseCase(transactionRepository: TransactionRepository): PurchaseUseCase {
        return PurchaseUseCase(transactionRepository)
    }

    @Provides
    @Singleton
    fun provideRefundUseCase(transactionRepository: TransactionRepository): RefundUseCase {
        return RefundUseCase(transactionRepository)
    }

    @Provides
    @Singleton
    fun provideTransactionUseCase(transactionRepository: TransactionRepository): TransactionUseCase {
        return TransactionUseCase(transactionRepository)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(transactionService: TransactionService): TransactionRepository{
        return TransactionRepositoryImp(transactionService)
    }

    @Provides
    @Singleton
    fun provideResource(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProvider(context)
    }
}