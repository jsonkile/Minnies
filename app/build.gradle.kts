import com.android.build.api.dsl.ApplicationDefaultConfig

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

        testInstrumentationRunner = "com.demo.minnies.HiltTestRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("/Users/jayson/AndroidStudioProjects/Minnies/keystores/upload.jks")
            keyAlias = "upload"
            storePassword = "d^U97HU2%BVd"
            keyPassword = "d^U97HU2%BVd"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
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
    val coroutinesVersion: String by rootProject.extra
    val jUnitVersion: String by rootProject.extra
    val testRunnerVersion: String by rootProject.extra
    val testCoreVersion: String by rootProject.extra
    val accompanistSystemControllerVersion: String by rootProject.extra
    val hiltNavigationComposeVersion: String by rootProject.extra

    implementation(project(":shared"))
    implementation(project(":notifications"))
    implementation(project(":orders"))
    implementation(project(":auth"))
    implementation(project(":cart"))
    implementation(project(":shop"))

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    implementation("androidx.navigation:navigation-testing:$navigationVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")

    //ui
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistSystemControllerVersion")

    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    androidTestImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test:core:$testCoreVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("androidx.navigation:navigation-testing:$navigationVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    androidTestImplementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    androidTestImplementation(kotlin("reflect"))
}

fun ApplicationDefaultConfig.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue = project.properties[gradlePropertyName] as? String
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".toUpperCase()
    buildConfigField("String", androidResourceName, "\"$propertyValue\"")
}

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.toLowerCase() }

kapt {
    correctErrorTypes = true
}