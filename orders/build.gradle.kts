plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.demo.minnies.orders"
    compileSdk = libs.versions.compilesdk.get().toInt()

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = libs.versions.minsdk.get().toInt()
        testInstrumentationRunner = "com.demo.minnies.orders.HiltTestRunner"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":auth"))

    val composeBom = platform(libs.composebom)
    implementation(composeBom)
    implementation(libs.bundles.ui)
    debugImplementation(libs.compose.manifest)
    debugImplementation(libs.compose.tooling.preview)

    kapt(libs.dagger.hilt.compiler)

    testImplementation(libs.bundles.test)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.test.android)
    kaptAndroidTest(libs.dagger.hilt.compiler)
    androidTestImplementation(kotlin("reflect"))
}

kapt {
    correctErrorTypes = true
}