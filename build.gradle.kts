// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.android.test) apply false
}

ext {
    extra["appId"] = "com.demo.minnies"
    extra["appVersionCode"] = 1
    extra["appVersionName"] = "1.0"
    extra["javaVersion"] = JavaVersion.VERSION_11
    extra["jvmTargetVersion"] = "11"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}