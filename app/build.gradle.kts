import com.android.build.api.dsl.ApplicationDefaultConfig

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ktlint.android)
}

android {
    val appVersionCode: Int by rootProject.extra
    val appVersionName: String by rootProject.extra
    val appId: String by rootProject.extra

    namespace = appId
    compileSdk = libs.versions.compilesdk.get().toInt()

    defaultConfig {
        applicationId = appId
        minSdk = libs.versions.minsdk.get().toInt()
        targetSdk = libs.versions.targetsdk.get().toInt()
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "com.demo.minnies.HiltTestRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file(rootDir.canonicalPath + "/upload.jks")
            keyAlias = "upload"
            storePassword = "d^U97HU2%BVd"
            keyPassword = "d^U97HU2%BVd"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }

        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }

    flavorDimensions += "version"

    productFlavors {
        create("premium") {
            applicationIdSuffix = ".paid"
            resValue("string", "app_name","Minnies Premium")
        }

        create("free") {
            applicationIdSuffix = ".free"
            resValue("string", "app_name","Minnies")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
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

ktlint {
    android.set(true)
    ignoreFailures.set(false)
    disabledRules.set(setOf("final-newline", "no-wildcard-imports"))
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":notifications"))
    implementation(project(":orders"))
    implementation(project(":auth"))
    implementation(project(":cart"))
    implementation(project(":products"))

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    val composeBom = platform(libs.composebom)
    implementation(composeBom)
    implementation(libs.bundles.ui)
    implementation(libs.splashscreen)
    implementation(libs.systemuicontroller)
    debugImplementation(libs.compose.manifest)
    debugImplementation(libs.compose.tooling.preview)

    testImplementation(libs.bundles.test)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.test.android)
    kaptAndroidTest(libs.dagger.hilt.compiler)
    androidTestImplementation(kotlin("reflect"))

    implementation(libs.profile.installer)
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