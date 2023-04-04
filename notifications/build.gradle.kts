plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.demo.minnies.notifications"
    compileSdk = libs.versions.compilesdk.get().toInt()

    flavorDimensions += "version"

    productFlavors {
        create("premium")

        create("free")
    }

    defaultConfig {
        minSdk = libs.versions.minsdk.get().toInt()
    }
}

dependencies {
    implementation(project(":shared"))
    kapt(libs.dagger.hilt.compiler)
}