plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")

    id("com.google.dagger.hilt.android")
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
        val composeCompilerVersion: String by rootProject.extra

        kotlinCompilerExtensionVersion = composeCompilerVersion
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
    val hiltVersion: String by rootProject.extra
    val roomVersion: String by rootProject.extra
    val coroutinesVersion: String by rootProject.extra
    val jUnitVersion: String by rootProject.extra
    val testRunnerVersion: String by rootProject.extra
    val testCoreVersion: String by rootProject.extra
    val accompanistSystemControllerVersion: String by rootProject.extra

    implementation(project(":shared"))
    implementation(project(":notifications"))
    implementation(project(":orders"))
    implementation(project(":auth"))
    implementation(project(":cart"))
    implementation(project(":shop"))

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    //ui
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistSystemControllerVersion")

    //room
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    androidTestImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test:core:$testCoreVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("androidx.navigation:navigation-testing:$navigationVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    androidTestImplementation("androidx.room:room-testing:$roomVersion")
    androidTestImplementation(kotlin("reflect"))
}

kapt {
    correctErrorTypes = true
}