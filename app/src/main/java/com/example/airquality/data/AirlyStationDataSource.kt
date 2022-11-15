package com.example.airquality.data

import com.example.airquality.entity.AQStation
import com.example.airquality.logic.repository.RemoteStationsRepository
import retrofit2.http.GET
import javax.inject.Inject

class AirlyStationDataSource @Inject constructor(private val airlyService: AirlyService) :
    RemoteStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        return airlyService.getInstallations().map {
            AQStation(
                id = it.id.toString(),
                name = it.address?.displayAddress2 ?: "Unknowwn",
                city = it.address?.city ?: "Unknowwn",
                sponsor = it.sponsor?.displayName ?: "Unknowwn",
                sponsorImage = it.sponsor?.logo
            )
        }
    }

    companion object {
        const val HOST = "https://airapi.airly.eu/v2/"
    }

    interface AirlyService {
        @GET("installations/nearest?lat=50.062006&lng=19.940984&maxDistanceKM=5&maxResults=100")
        suspend fun getInstallations(): List<Installation>
    }
}