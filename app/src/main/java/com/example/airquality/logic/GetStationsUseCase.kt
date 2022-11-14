package com.example.airquality.logic

import com.example.airquality.entity.AQStation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetStationsUseCase @Inject constructor(private val remoteStationsRepository: RemoteStationsRepository) {
    fun execute(): List<AQStation> {
        return listOf(
            AQStation(
                id = "123",
                name = "Name",
                city = "Kraków",
                sponsor = "TEST",
                sponsorImage = null
            )
        )
    }
}