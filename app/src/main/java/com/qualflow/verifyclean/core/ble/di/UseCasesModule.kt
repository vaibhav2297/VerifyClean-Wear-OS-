package com.qualflow.verifyclean.core.ble.di

import com.qualflow.verifyclean.core.ble.domain.usecases.ParseDescriptor
import com.qualflow.verifyclean.core.ble.domain.usecases.ParseRead
import com.qualflow.verifyclean.core.ble.domain.usecases.ParseService
import com.qualflow.verifyclean.core.ble.domain.usecases.ParseWrite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun bindParseService(): ParseService =
        ParseService()

    @Provides
    @Singleton
    fun bindParseRead(): ParseRead =
        ParseRead()

    @Provides
    @Singleton
    fun bindParseWrite(): ParseWrite =
        ParseWrite()

    @Provides
    @Singleton
    fun bindParsDescriptor(): ParseDescriptor =
        ParseDescriptor()


}