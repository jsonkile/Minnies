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

    //core
    api("androidx.core:core-ktx:1.9.0")
    api("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    api("androidx.compose.ui:ui-tooling-preview:$composeVersion")

    api("androidx.navigation:navigation-compose:$navigationVersion")

    //room
    api("androidx.room:room-runtime:$roomVersion")
    api("androidx.room:room-ktx:$roomVersion")

    //ui
    api("androidx.appcompat:appcompat:1.5.1")
    api("androidx.compose.material3:material3:1.1.0-alpha01")
    api("androidx.compose.material:material-icons-extended:1.3.0")

    //lifecycle
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")

    //utils
    api("com.jakewharton.timber:timber:5.0.1")

    //test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test:core:1.5.0-beta01")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")

}