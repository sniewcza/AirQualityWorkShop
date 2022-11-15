package com.example.airquality.logic.repository

import com.example.airquality.entity.AQStation

class InMemoryStationsRepository:LocalStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        return emptyList()
    }

    override suspend fun save(stations: List<AQStation>) {

    }
}