import com.android.build.api.dsl.LibraryDefaultConfig

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlinx-serialization")

    kotlin("kapt")
}

android {
    namespace = "com.demo.minnies.shared"
    val compileSdkVersion: Int by rootProject.extra
    val minSdkVersion: Int by rootProject.extra

    compileSdk = compileSdkVersion

    defaultConfig {
        minSdk = minSdkVersion

        testInstrumentationRunner = "com.demo.minnies.shared.HiltTestRunner"

        buildConfigFieldFromGradleProperty("appName")
    }

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
    val composeBomVersion: String by rootProject.extra
    val navigationVersion: String by rootProject.extra
    val hiltVersion: String by rootProject.extra
    val roomVersion: String by rootProject.extra
    val coilVersion: String by rootProject.extra
    val timberVersion: String by rootProject.extra
    val jUnitVersion: String by rootProject.extra
    val testRunnerVersion: String by rootProject.extra
    val testCoreVersion: String by rootProject.extra
    val constraintLayoutVersion: String by rootProject.extra
    val androidXCoreVersion: String by rootProject.extra
    val testExtJunitVersion: String by rootProject.extra
    val appcompatversion: String by rootProject.extra
    val composeMaterialVersion: String by rootProject.extra
    val composeMaterialIconsVersion: String by rootProject.extra
    val coroutinesVersion: String by rootProject.extra
    val hiltNavigationComposeVersion: String by rootProject.extra
    val kotlinSerializationVersion: String by rootProject.extra
    val easyValidation: String by rootProject.extra
    val apacheCommons: String by rootProject.extra

    api(project(":database"))

    //core
    api("androidx.core:core-ktx:$androidXCoreVersion")
    api("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    val composeBom = platform("androidx.compose:compose-bom:$composeBomVersion")
    api(composeBom)
    androidTestImplementation(composeBom)

    //ui
    api("androidx.appcompat:appcompat:$appcompatversion")
    api("androidx.compose.material:material")
    api("androidx.compose.material3:material3")
    api("androidx.compose.material:material-icons-extended")
    api("io.coil-kt:coil:$coilVersion")
    api("io.coil-kt:coil-compose:$coilVersion")
    api("androidx.constraintlayout:constraintlayout-compose:$constraintLayoutVersion")
    api("androidx.navigation:navigation-compose:$navigationVersion")
    api("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")
    api("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation("androidx.compose.ui:ui-tooling")

    //lifecycle
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")

    //utils
    api("com.jakewharton.timber:timber:$timberVersion")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    implementation("com.wajahatkarim:easyvalidation-core:$easyValidation")
    implementation("org.apache.commons:commons-lang3:$apacheCommons")

    //test
    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test.ext:junit:$testExtJunitVersion")
    androidTestImplementation("androidx.test:core:$testCoreVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

}

fun LibraryDefaultConfig.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue = project.properties[gradlePropertyName] as? String
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".toUpperCase()
    buildConfigField("String", androidResourceName, "\"$propertyValue\"")
}

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.toLowerCase() }
