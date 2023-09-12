package com.till.paymentapplicationpoc.di

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Resource provider to access android resources
 */
@Singleton
class ResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getString(@StringRes stringId: Int): String = context.getString(stringId)
}