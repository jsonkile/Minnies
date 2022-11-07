plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.demo.minnies.shared"
    val compileSdkVersion: Int by rootProject.extra

    compileSdk = compileSdkVersion

    buildFeatures {
        compose = true
    }

    composeOptions {
        val composeCompilerVersion: String by rootProject.extra

        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
}

dependencies {
    val lifecycleVersion: String by rootProject.extra
    val composeVersion: String by rootProject.extra
    val navigationVersion: String by rootProject.extra
    val hiltVersion: String by rootProject.extra
    val roomVersion: String by rootProject.extra
    val coilVersion: String by rootProject.extra
    val timberVersion: String by rootProject.extra
    val jUnitVersion: String by rootProject.extra
    val testRunnerVersion: String by rootProject.extra
    val testCoreVersion: String by rootProject.extra
    val constraintLayoutVersion: String by rootProject.extra
    val androidXCoreVersion: String by rootProject.extra
    val testExtJunitVersion: String by rootProject.extra
    val appcompatVersion: String by rootProject.extra
    val composeMaterialVersion: String by rootProject.extra
    val composeMaterialIconsVersion: String by rootProject.extra

    //core
    api("androidx.core:core-ktx:$androidXCoreVersion")
    api("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    api("androidx.compose.ui:ui-tooling-preview:$composeVersion")

    //room
    api("androidx.room:room-runtime:$roomVersion")
    api("androidx.room:room-ktx:$roomVersion")

    //ui
    api("androidx.appcompat:appcompat:$appcompatVersion")
    api("androidx.compose.material3:material3:$composeMaterialVersion")
    api("androidx.compose.material:material-icons-extended:$composeMaterialIconsVersion")
    api("io.coil-kt:coil:$coilVersion")
    api("io.coil-kt:coil-compose:$coilVersion")
    api("androidx.constraintlayout:constraintlayout-compose:$constraintLayoutVersion")
    api("androidx.navigation:navigation-compose:$navigationVersion")


    //lifecycle
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")

    //utils
    api("com.jakewharton.timber:timber:$timberVersion")

    //test
    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test.ext:junit:$testExtJunitVersion")
    androidTestImplementation("androidx.test:core:$testCoreVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")

}