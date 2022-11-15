package com.example.airquality.logic

import com.example.airquality.entity.AQStation
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class GetStationsUseCaseTest {
    @Test
    fun init_DoesNotMakeAnyRemoteOrLocalCalls() {
        val remote = MockRemoteStationsRepository()
        GetStationsUseCase(remote)
        assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeMakesOneCallToRemote() = runBlocking {
        val remote = MockRemoteStationsRepository()
        val sut = GetStationsUseCase(remote)
        sut.execute()
        assertEquals(1, remote.getAllCallCount)
    }
}


class MockRemoteStationsRepository : RemoteStationsRepository {
    val getAllCalled
        get() = getAllCallCount > 0
    var getAllCallCount = 0

    override suspend fun getAll(): List<AQStation> {
        getAllCallCount++
        return emptyList()
    }

}