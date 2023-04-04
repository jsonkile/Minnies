plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.demo.minnies.database"

    compileSdk = libs.versions.compilesdk.get().toInt()

    flavorDimensions += "version"

    productFlavors {
        create("premium")

        create("free")
    }

    defaultConfig {
        minSdk = libs.versions.minsdk.get().toInt()
        testInstrumentationRunner = "com.demo.minnies.database.HiltTestRunner"
    }
}

dependencies {
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.bundles.room)

    implementation(libs.bundles.core)
    kapt(libs.dagger.hilt.compiler)

    api(libs.datastore)
    api(libs.datastore.preference)

    testImplementation(libs.junit)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.dagger.hilt.testing)
    kaptAndroidTest(libs.dagger.hilt.compiler)
    androidTestImplementation(libs.room.testing)
}