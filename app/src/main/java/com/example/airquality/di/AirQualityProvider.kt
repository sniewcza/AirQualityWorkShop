package com.example.airquality.di

import com.example.airquality.logic.FakeRemoteStationsRepository
import com.example.airquality.logic.GetStationsUseCase
import com.example.airquality.logic.RemoteStationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AirQualityProvider {

    @Provides
    @Singleton
    fun provideRemoteStationsRepository(): RemoteStationsRepository {
        return FakeRemoteStationsRepository()
    }
}