plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.demo.minnies.orders"
    val compileSdkVersion: Int by rootProject.extra
    val minSdkVersion: Int by rootProject.extra

    compileSdk = compileSdkVersion

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = minSdkVersion
        testInstrumentationRunner = "com.demo.minnies.orders.HiltTestRunner"
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
    val hiltNavigationComposeVersion: String by rootProject.extra
    val navigationVersion: String by rootProject.extra
    val testCoreVersion: String by rootProject.extra
    val turbineVersion: String by rootProject.extra
    val mockkVersion: String by rootProject.extra

    implementation(project(":shared"))
    implementation(project(":auth"))

    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    implementation("androidx.navigation:navigation-testing:$navigationVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test:core:$testCoreVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("androidx.navigation:navigation-testing:$navigationVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    androidTestImplementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    androidTestImplementation("app.cash.turbine:turbine:$turbineVersion")
    testImplementation("app.cash.turbine:turbine:$turbineVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    androidTestImplementation(kotlin("reflect"))
    testImplementation("io.mockk:mockk-android:$mockkVersion")
    testImplementation("io.mockk:mockk-agent:$mockkVersion")
}

kapt {
    correctErrorTypes = true
}