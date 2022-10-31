plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.demo.minnies.cart"
    val compileSdkVersion: Int by rootProject.extra

    compileSdk = compileSdkVersion

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":database"))
}