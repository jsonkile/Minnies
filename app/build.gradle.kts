plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")

    kotlin("kapt")
}

android {
    val compileSdkVersion: Int by rootProject.extra
    val targetSdkVersion: Int by rootProject.extra
    val appVersionCode: Int by rootProject.extra
    val appVersionName: String by rootProject.extra
    val appId: String by rootProject.extra
    val minSdkVersion: Int by rootProject.extra

    namespace = appId
    compileSdk = compileSdkVersion

    defaultConfig {
        applicationId = appId
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "com.demo.minnies.shared.HiltTestRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }

    compileOptions {
        val javaVersion: JavaVersion by rootProject.extra

        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        val jvmTargetVersion: String by rootProject.extra
        jvmTarget = jvmTargetVersion
    }
}

dependencies {
    val composeVersion: String by rootProject.extra
    val navigationVersion: String by rootProject.extra

    implementation(project(":shared"))
    implementation(project(":database"))
    implementation(project(":notifications"))
    implementation(project(":orders"))
    implementation(project(":auth"))
    implementation(project(":cart"))
    implementation(project(":shop"))

    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:core:1.5.0-beta01")
    androidTestImplementation("androidx.navigation:navigation-testing:$navigationVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    androidTestImplementation(kotlin("reflect"))
}

kapt {
    correctErrorTypes = true
}