package com.apap.cameraManager

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.apap.cameraManager.domain.usecase.Authorize
import com.apap.cameraManager.domain.usecase.GetDevices
import com.apap.cameraManager.domain.usecase.LogIn
import com.apap.cameraManager.presentation.view.CameraManagerNavigation
import com.apap.cameraManager.presentation.view.MainActivity
import com.apap.cameraManager.presentation.view.Route
import com.apap.cameraManager.presentation.viewModel.MainViewModel
import com.apap.cameraManager.testUtils.FakeDeviceRepository
import com.apap.cameraManager.testUtils.FakeDevicesCache
import com.apap.cameraManager.testUtils.FakeLoginRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var viewModel: MainViewModel
    private lateinit var logIn: LogIn
    private lateinit var getDevices: GetDevices
    private lateinit var authorize: Authorize

    private val loginRepository = FakeLoginRepository()
    private val deviceRepository = FakeDeviceRepository()
    private val devicesCache = FakeDevicesCache()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CameraManagerNavigation(navController = navController, onBackPressed = {})
        }
        logIn = LogIn(loginRepository)
        authorize = Authorize(loginRepository)
        getDevices = GetDevices(deviceRepository)
    }

    @Test
    fun verify_nav_host_start_destination() {
        composeTestRule.onNodeWithTag("main_screen").assertIsDisplayed()
    }

    @Test
    fun verify_toolbar_and_camera_image_and_loader_present_on_startup(): Unit = with(composeTestRule) {
        onNodeWithTag("toolbar").assertIsDisplayed()
        onNodeWithTag("loading_image").assertIsDisplayed()
        onNodeWithTag("loader").assertIsDisplayed()
        onNodeWithTag("back_icon").assertDoesNotExist()
    }

    @Test
    fun verify_search_bar_not_visible_on_startup() {
        composeTestRule.onNodeWithTag("search_bar").assertDoesNotExist()
    }

    @Test
    fun verify_search_bar_on_devices_list_retrieved(): Unit = with(composeTestRule) {
        initViewModelForSuccess()
        waitUntil(timeoutMillis = 20000L) {
            onAllNodesWithText("Search…")
                .fetchSemanticsNodes().size == 1
        }
        onNodeWithTag("search_bar").assertIsDisplayed()
    }

    @Test
    fun verify_device_list_item_on_devices_list_retrieved(): Unit = with(composeTestRule) {
        initViewModelForSuccess()
        waitUntil(timeoutMillis = 20000L) {
            onAllNodesWithTag("devices_list")
                .fetchSemanticsNodes().size == 1
        }
    }

    @Test
    fun navigate_to_details_screen_on_camera_clicked(): Unit = with(composeTestRule) {
        initViewModelForSuccess()
        waitUntil(timeoutMillis = 20000L) {
            onAllNodesWithTag("devices_list")
                .fetchSemanticsNodes().size == 1
        }

        onNodeWithText("Amsterdam").performClick()
        val route = navController.currentDestination?.route
        assertEquals(route, Route.Details.name)
        onNodeWithTag("camera_details_card").assertIsDisplayed()
        onNodeWithTag("card_title").assertIsDisplayed()
        onNodeWithTag("camera_id_card_text").assertIsDisplayed()
        onNodeWithTag("service_status_card_text").assertIsDisplayed()
        onNodeWithTag("timezone_card_text").assertIsDisplayed()
        onNodeWithTag("ip_address_card_text").assertIsDisplayed()
        onNodeWithTag("owner_account_name_card_text").assertIsDisplayed()
    }

    @Test
    fun navigate_from_details_to_main_screen_on_back_button_pressed(): Unit = with(composeTestRule) {
        initViewModelForSuccess()
        waitUntil(timeoutMillis = 20000L) {
            onAllNodesWithTag("devices_list")
                .fetchSemanticsNodes().size == 1
        }

        onNodeWithText("Amsterdam").performClick()
        waitUntil(timeoutMillis = 10000L) {
            onAllNodesWithTag("back_icon")
                .fetchSemanticsNodes().size == 1
        }
        activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        waitUntil(timeoutMillis = 10000L) {
            onAllNodesWithTag("main_screen")
                .fetchSemanticsNodes().size == 1
        }
    }

    @Test
    fun stay_on_main_screen_on_back_pressed_in_main_screen(): Unit = with(composeTestRule) {
        initViewModelForSuccess()
        onNodeWithTag("main_screen").assertIsDisplayed()
        activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        waitForIdle()
        onNodeWithTag("main_screen").assertIsDisplayed()
    }

    private fun initViewModelForSuccess() {
        loginRepository.setToken("token")
        loginRepository.setAuthorized(true)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = MainViewModel(authorize, devicesCache, getDevices, logIn)
    }
}
