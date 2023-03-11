plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.demo.minnies.cart"
    compileSdk = libs.versions.compilesdk.get().toInt()

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = libs.versions.minsdk.get().toInt()
        testInstrumentationRunner = "com.demo.minnies.cart.HiltTestRunner"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":auth"))

    kapt(libs.dagger.hilt.compiler)

    val composeBom = platform(libs.composebom)
    implementation(composeBom)
    implementation(libs.bundles.ui)
    debugImplementation(libs.compose.manifest)
    debugImplementation(libs.compose.tooling.preview)

    testImplementation(libs.bundles.test)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.test.android)
    kaptAndroidTest(libs.dagger.hilt.compiler)
    androidTestImplementation(kotlin("reflect"))
}

kapt {
    correctErrorTypes = true
}