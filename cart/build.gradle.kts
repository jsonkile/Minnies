plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")

    kotlin("kapt")
}

android {
    namespace = "com.demo.minnies.cart"
    val compileSdkVersion: Int by rootProject.extra
    val minSdkVersion: Int by rootProject.extra

    compileSdk = compileSdkVersion

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = minSdkVersion
        testInstrumentationRunner = "com.demo.minnies.cart.HiltTestRunner"
    }

    composeOptions {
        val composeCompilerVersion: String by rootProject.extra

        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
}

dependencies {
    val hiltVersion: String by rootProject.extra
    val coroutinesVersion: String by rootProject.extra
    val composeVersion: String by rootProject.extra
    val jUnitVersion: String by rootProject.extra
    val testRunnerVersion: String by rootProject.extra
    val turbineVersion: String by rootProject.extra

    implementation(project(":shared"))
    implementation(project(":auth"))

    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    androidTestImplementation("app.cash.turbine:turbine:$turbineVersion")

    testImplementation("app.cash.turbine:turbine:$turbineVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}

kapt {
    correctErrorTypes = true
}