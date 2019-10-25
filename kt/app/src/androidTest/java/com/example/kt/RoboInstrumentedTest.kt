package com.example.kt

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.hamcrest.CoreMatchers.notNullValue

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

private const val BASIC_SAMPLE_PACKAGE = "ee.mtakso.client"
private const val LAUNCH_TIMEOUT = 5000L
private const val STRING_TO_BE_TYPED = "Orsen"

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class RoboInstrumentedTest {
    private lateinit var device: UiDevice

    @Before
    fun startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT
        )

        // Launch the app
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(
            BASIC_SAMPLE_PACKAGE
        ).apply {
            // Clear out any previous instances
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
            LAUNCH_TIMEOUT
        )
    }

    @Test
    fun openApp() {
        device.wait(
            Until.hasObject(By.res("ee.mtakso.client:id/setPickupButton").depth(0)),
            LAUNCH_TIMEOUT
        )

        device.findObject(
            UiSelector().resourceId("ee.mtakso.client:id/menu")
        ).click()

        device.findObject(
            UiSelector().resourceId("ee.mtakso.client:id/name").textContains("Промоакции")
        ).click()

        device.findObject(
            UiSelector().resourceId("ee.mtakso.client:id/promo_code_input")
        ).text = "ORSEN1000"

        device.findObject(
            UiSelector().resourceId("ee.mtakso.client:id/back_btn_add_promo_code")
        ).click()

        device.findObject(
            UiSelector().resourceId("ee.mtakso.client:id/setPickupButton")
        ).click()

        device.findObject(
            UiSelector().textContains("Введите место подачи")
        ).text = STRING_TO_BE_TYPED


        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        println(appContext.packageName)
        println(appContext.packageName)
        assertEquals(appContext.packageName, appContext.packageName)
    }

    @Test
    fun openSetting() {
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT
        )

        // Launch the app
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(
            "com.android.settings"
        ).apply {
            // Clear out any previous instances
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
            LAUNCH_TIMEOUT
        )

        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        println(appContext.packageName)
        println(appContext.packageName)
        assertEquals(appContext.packageName, appContext.packageName)
    }
}
