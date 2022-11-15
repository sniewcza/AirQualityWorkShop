package com.example.airquality.logic

import com.example.airquality.entity.AQStation
import com.example.airquality.logic.repository.LocalStationsRepository
import com.example.airquality.logic.repository.RemoteStationsRepository
import com.example.airquality.logic.usecase.GetStationsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetStationsUseCaseTest {

    lateinit var sut: GetStationsUseCase
    private lateinit var remote: MockRemoteStationsRepository
    private lateinit var local: MockLocalStationsRepository

    @Before
    fun setUp() {
        remote = MockRemoteStationsRepository()
        local = MockLocalStationsRepository()
        sut = GetStationsUseCase(remoteStationsRepository = remote, localStationsRepository = local)
    }

    @Test
    fun init_DoesNotMakeAnyRemoteOrLocalCalls() {
        assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeMakesOneCallToLocal() = runBlocking {
        sut.execute()
        assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeMakesCallToRemoteWhenLocalDataIsEmpty() = runBlocking {
        local.getAllResults = emptyList()

        sut.execute()

        assertEquals(true, remote.getAllCalled)
    }

    @Test
    fun executeDoesNotMakeCallToRemoteWhenLocalDataIsNotEmpty() = runBlocking {
        local.getAllResults = listOf(sampleAQStation1)

        sut.execute()

        assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeDoesMakeOneCallToLocal() = runBlocking {
        sut.execute()

        assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeDoesMakeOneCallToLocalForNonEmptyData() = runBlocking {
        local.getAllResults = listOf(sampleAQStation1)

        sut.execute()

        assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeReturnsRemoteStationsWhenRemoteStationRepositoryIsCalled() = runBlocking {
        local.getAllResults = emptyList()
        remote.getAllResults = listOf(sampleAQStation1)

        val actual = sut.execute()

        assertEquals("1", actual.first().id)
    }

    @Test
    fun executeReturnsLocalStationsWhenLocalStationRepositoryIsCalled() = runBlocking {
        local.getAllResults = listOf(sampleAQStation1)

        val actual = sut.execute()

        assertEquals("1", actual.first().id)
    }

    @Test
    fun executeSavesStationsToLocalWhenRemoteIsNonEmpty() = runBlocking {
        local.getAllResults = emptyList()
        remote.getAllResults = listOf(sampleAQStation1)

        sut.execute()

        assertEquals(true, local.saveCalled)
        assertEquals("1", local.saveReceivedArguments.first().id)
    }

    @Test
    fun executeReturnsValidLocalListStations() = runBlocking {
        val sampleAQStation2 = AQStation("2", "", "", "", "")
        local.getAllResults = listOf(sampleAQStation1, sampleAQStation2)

        val actual = sut.execute()

        assertEquals("1", actual.first().id)
        assertEquals("2", actual[1].id)
    }

    var sampleAQStation1 = AQStation("1", "", "", "", "")

}

class MockLocalStationsRepository : LocalStationsRepository {
    val getAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0
    var getAllResults: List<AQStation> = emptyList()

    val saveCalled: Boolean
        get() = saveCallsCount > 0
    var saveCallsCount: Int = 0
    var saveReceivedArguments: List<AQStation> = emptyList()

    override suspend fun getAll(): List<AQStation> {
        getAllCallsCount++
        return getAllResults
    }

    override suspend fun save(stations: List<AQStation>) {
        saveReceivedArguments = stations
        saveCallsCount++
    }

}

class MockRemoteStationsRepository : RemoteStationsRepository {
    val getAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0
    var getAllResults: List<AQStation> = emptyList()

    override suspend fun getAll(): List<AQStation> {
        getAllCallsCount++
        return getAllResults
    }
}
