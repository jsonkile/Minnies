import com.android.build.api.dsl.LibraryDefaultConfig

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.demo.minnies.shared"
    compileSdk = libs.versions.compilesdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minsdk.get().toInt()
        testInstrumentationRunner = "com.demo.minnies.shared.HiltTestRunner"
        buildConfigFieldFromGradleProperty("appName")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
    }
}

dependencies {
    api(project(":database"))

    api(libs.bundles.core)
    kapt(libs.dagger.hilt.compiler)

    val composeBom = platform(libs.composebom)
    implementation(composeBom)
    implementation(libs.bundles.ui)
    debugImplementation(libs.compose.manifest)
    debugImplementation(libs.compose.tooling.preview)

    implementation(libs.easyvalidation)
    implementation(libs.commons)

    testImplementation(libs.bundles.test)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.test.android)
    kaptAndroidTest(libs.dagger.hilt.compiler)

}

fun LibraryDefaultConfig.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue = project.properties[gradlePropertyName] as? String
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".toUpperCase()
    buildConfigField("String", androidResourceName, "\"$propertyValue\"")
}

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.toLowerCase() }
