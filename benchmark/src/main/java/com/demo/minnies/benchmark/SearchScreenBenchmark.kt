package com.demo.minnies.benchmark

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
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
class SearchScreenBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun runSearch() = benchmarkRule.measureRepeated(
        packageName = context.getString(R.string.package_name),
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()

        performSearch()
    }
}

fun MacrobenchmarkScope.performSearch() {
    device.waitForIdle()
    val searchNavBtn = device.findObject(By.res("Search_nav_button_test_tag"))
    searchNavBtn.click()

    val searchBar = device.findObject(By.res("search_bar_test_tag"))
    searchBar.click()
    searchBar.text = "puma"
    device.waitForIdle()
    device.click(device.displayWidth - 3, device.displayHeight - 3)

    val searchResultList = device.findObject(By.res("SEARCH_SCREEN_RESULTS_LIST_TEST_TAG"))
    searchResultList.setGestureMargin(device.displayWidth / 5)
    searchResultList.fling(Direction.DOWN)
    searchResultList.children[0].click()
}