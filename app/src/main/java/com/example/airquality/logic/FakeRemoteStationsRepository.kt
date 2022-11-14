package com.example.airquality.logic

import com.example.airquality.entity.AQStation
import javax.inject.Singleton

@Singleton
class FakeRemoteStationsRepository : RemoteStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        return emptyList()
    }
}