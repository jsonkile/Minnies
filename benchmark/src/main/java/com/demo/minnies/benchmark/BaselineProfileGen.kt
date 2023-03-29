package com.demo.minnies.benchmark

import android.content.Context
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileGen {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun generate() =
        baselineProfileRule.collectBaselineProfile(context.getString(R.string.package_name)) {
            pressHome()
            startActivityAndWait()

            performSearch()
        }
}