package com.demo.minnies.benchmark

import android.content.Context
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeScreenBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun runHomeScreen() = benchmarkRule.measureRepeated(
        packageName = context.getString(R.string.package_name),
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()

        device.waitForIdle()

        val productsList = device.findObject(By.res("products_list"))
        productsList.setGestureMargin(device.displayWidth / 5)
        productsList.fling(Direction.DOWN)

        device.wait(Until.hasObject(By.text("Boots, Kicks and Trainers")), 5000)
    }

    @Test
    fun runHomeScreenToDetailsScreen() = benchmarkRule.measureRepeated(
        packageName = context.getString(R.string.package_name),
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()

        device.waitForIdle()

        device.findObject(By.res("SHOP_ITEM_CARD_TEST_TAG_FEATURED_0")).click()
        device.wait(Until.hasObject(By.text("Add to cart")), 5000)
    }

    @Test
    fun runHomeScreenToSignUpScreen() = benchmarkRule.measureRepeated(
        packageName = context.getString(R.string.package_name),
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()

        device.waitForIdle()

        device.findObject(By.text("Create an account")).click()
        device.wait(Until.hasObject(By.text("Sign up")), 5000)
    }
}