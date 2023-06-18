package com.apap.cameraManager.presentation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.apap.cameraManager.data.repository.DevicesCache
import com.apap.cameraManager.domain.model.Authentication
import com.apap.cameraManager.domain.model.Authorization
import com.apap.cameraManager.domain.model.Device
import com.apap.cameraManager.domain.model.FailureCause
import com.apap.cameraManager.domain.usecase.Authorize
import com.apap.cameraManager.domain.usecase.GetDevices
import com.apap.cameraManager.domain.usecase.LogIn
import com.apap.cameraManager.presentation.view.LoadingState
import com.apap.cameraManager.testUtils.MainDispatcherRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var authorize: Authorize
    @MockK
    private lateinit var devicesCache: DevicesCache
    @MockK
    private lateinit var getDevices: GetDevices
    @MockK
    private lateinit var logIn: LogIn

    private lateinit var subject: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    private fun initViewModel() {
        subject = MainViewModel(
            authorize = authorize,
            devicesCache = devicesCache,
            getDevices = getDevices,
            logIn = logIn,
        )
    }

    @Test
    fun `should emit failure when authentication fails`() = runTest {
        coEvery { logIn() } returns null

        initViewModel()

        coVerify { logIn() }
        subject.loadingStateFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(LoadingState.Failure)
            expectNoEvents()
        }
        subject.failureCauseFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(FailureCause.Authentication)
            expectNoEvents()
        }
    }

    @Test
    fun `should emit failure when authorization fails`() = runTest {
        coEvery { logIn() } returns Authentication("token")
        coEvery { authorize("token") } returns null

        initViewModel()

        coVerify { logIn() }
        coVerify { authorize("token") }
        subject.loadingStateFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(LoadingState.Failure)
            expectNoEvents()
        }
        subject.failureCauseFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(FailureCause.Authentication)
            expectNoEvents()
        }
    }

    @Test
    fun `should emit failure when get devices returns empty list`() = runTest {
        val expectedDeviceList = emptyList<Device>()
        coEvery { logIn() } returns Authentication("token")
        coEvery {
            authorize("token")
        } returns Authorization("test", "id", "userId")
        coEvery {
            getDevices("test")
        } returns expectedDeviceList

        initViewModel()

        coVerify { logIn() }
        coVerify { authorize("token") }
        coVerify { getDevices("test") }
        subject.loadingStateFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(LoadingState.Failure)
            expectNoEvents()
        }
        subject.failureCauseFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(FailureCause.Data)
            expectNoEvents()
        }
    }

    @Test
    fun `should emit failure when get devices returns null`() = runTest {
        val expectedDeviceList = null

        coEvery { logIn() } returns Authentication("token")
        coEvery {
            authorize("token")
        } returns Authorization("test", "id", "userId")
        coEvery {
            getDevices("test")
        } returns expectedDeviceList

        initViewModel()

        coVerify { logIn() }
        coVerify { authorize("token") }
        coVerify { getDevices("test") }
        subject.loadingStateFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(LoadingState.Failure)
            expectNoEvents()
        }
        subject.failureCauseFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(FailureCause.Data)
            expectNoEvents()
        }
    }

    @Test
    fun `should save devices to cache and emit done when camera devices are found`() = runTest {
        val expectedDeviceList = listOf(
            Device(
                deviceName = "Device 1",
                ipAddress = "127.0.0.1",
                serviceStatus = "ATTD",
                cameraId = "0",
                ownerAccountName = "John Doe",
                timezone = "US/Central"
            ),
            Device(
                deviceName = "Device 2",
                ipAddress = "127.0.0.1",
                serviceStatus = "ERSE",
                cameraId = "1",
                ownerAccountName = "Lucas Hood",
                timezone = "US/Central"
            ),
        )

        coEvery { logIn() } returns Authentication("token")
        coEvery {
            authorize("token")
        } returns Authorization("test", "id", "userId")
        coEvery {
            getDevices("test")
        } returns expectedDeviceList
        every { devicesCache.save(any()) } just runs

        initViewModel()

        coVerify { logIn() }
        coVerify { authorize("token") }
        coVerify { getDevices("test") }
        verify { devicesCache.save(expectedDeviceList)}
        subject.loadingStateFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(LoadingState.Done)
            expectNoEvents()
        }
        subject.devicesFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(expectedDeviceList)
        }
        subject.failureCauseFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(FailureCause.None)
            expectNoEvents()
        }
    }

    @Test
    fun `should emit empty devices list on search failure`() = runTest {
        val expectedDeviceList = listOf(
            Device(
                deviceName = "Device 1",
                ipAddress = "127.0.0.1",
                serviceStatus = "ATTD",
                cameraId = "0",
                ownerAccountName = "John Doe",
                timezone = "US/Central"
            ),
            Device(
                deviceName = "Device 2",
                ipAddress = "127.0.0.1",
                serviceStatus = "ERSE",
                cameraId = "1",
                ownerAccountName = "Lucas Hood",
                timezone = "US/Central"
            ),
        )

        coEvery { logIn() } returns Authentication("token")
        coEvery {
            authorize("token")
        } returns Authorization("test", "id", "userId")
        coEvery {
            getDevices("test")
        } returns expectedDeviceList
        every { devicesCache.retrieve() } returns expectedDeviceList

        initViewModel()
        subject.onDeviceSearch("1!%434")

        verify { devicesCache.retrieve() }

        subject.devicesFlow.test {
            assertThat(expectMostRecentItem()).isEqualTo(emptyList<Device>())
        }
    }

    @Test
    fun `should emit correct devices on successful search`() = runTest {
        @Test
        fun `should emit empty devices list on search failure`() = runTest {
            val expectedDeviceList = listOf(
                Device(
                    deviceName = "Device 1",
                    ipAddress = "127.0.0.1",
                    serviceStatus = "ATTD",
                    cameraId = "0",
                    ownerAccountName = "John Doe",
                    timezone = "US/Central"
                ),
                Device(
                    deviceName = "Device 2",
                    ipAddress = "127.0.0.1",
                    serviceStatus = "ERSE",
                    cameraId = "1",
                    ownerAccountName = "Lucas Hood",
                    timezone = "US/Central"
                ),
            )

            coEvery { logIn() } returns Authentication("token")
            coEvery {
                authorize("token")
            } returns Authorization("test", "id", "userId")
            coEvery {
                getDevices("test")
            } returns expectedDeviceList
            every { devicesCache.save(any()) } just runs

            initViewModel()

            subject.onDeviceSearch("2")

            subject.devicesFlow.test {
                assertThat(expectMostRecentItem()).isEqualTo(expectedDeviceList[1])
            }
        }
    }
}