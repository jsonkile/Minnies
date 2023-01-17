// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20" apply false
}

ext {
    extra["appId"] = "com.demo.minnies"
    extra["compileSdkVersion"] = 33
    extra["targetSdkVersion"] = 33
    extra["minSdkVersion"] = 24
    extra["appVersionCode"] = 1
    extra["appVersionName"] = "1.0"
    extra["javaVersion"] = JavaVersion.VERSION_11
    extra["jvmTargetVersion"] = "11"

    extra["androidXCoreVersion"] = "1.9.0"
    extra["lifecycleVersion"] = "2.6.0-alpha02"
    extra["coroutinesVersion"] = "1.6.4"
    extra["hiltVersion"] = "2.44"
    extra["hiltNavigationComposeVersion"] = "1.0.0"

    extra["composeVersion"] = "1.3.0"
    extra["composeCompilerVersion"] = "1.3.2"
    extra["appcompatVersion"] = "1.5.1"
    extra["accompanistSystemControllerVersion"] = "0.27.0"
    extra["constraintLayoutVersion"] = "1.0.1"
    extra["navigationVersion"] = "2.5.2"
    extra["coilVersion"] = "2.2.2"
    extra["composeMaterialVersion"] = "1.1.0-alpha01"
    extra["composeMaterialIconsVersion"] = "1.3.0"

    extra["kotlinSerializationVersion"] = "1.4.1"

    extra["roomVersion"] = "2.4.3"
    extra["dataStoreVersion"] = "1.0.0"

    extra["jUnitVersion"] = "4.13.2"
    extra["testRunnerVersion"] = "1.4.0"
    extra["testCoreVersion"] = "1.5.0-beta01"
    extra["testExtJunitVersion"] = "1.1.3"
    extra["turbineVersion"] = "0.12.1"

    extra["timberVersion"] = "5.0.1"
    extra["easyValidation"] = "1.0.4"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}