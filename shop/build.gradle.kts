plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")
}

android {
    namespace = "com.demo.minnies.shop"
    val compileSdkVersion: Int by rootProject.extra
    val minSdkVersion: Int by rootProject.extra

    compileSdk = compileSdkVersion

    defaultConfig {
        minSdk = minSdkVersion
        testInstrumentationRunner = "com.demo.minnies.shop.HiltTestRunner"
    }

    buildFeatures {
        compose = true
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
    val roomVersion: String by rootProject.extra
    val jUnitVersion: String by rootProject.extra
    val testRunnerVersion: String by rootProject.extra
    val hiltNavigationComposeVersion: String by rootProject.extra

    implementation(project(":shared"))

    //room
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    androidTestImplementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}

kapt {
    correctErrorTypes = true
}