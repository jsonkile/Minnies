// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

ext {
    extra["appId"] = "com.demo.minnies"
    extra["compileSdkVersion"] = 33
    extra["targetSdkVersion"] = 33
    extra["minSdkVersion"] = 24
    extra["appVersionCode"] = 1
    extra["appVersionName"] = "1.0"

    extra["lifecycleVersion"] = "2.6.0-alpha02"
    extra["composeVersion"] = "1.3.0"
    extra["composeCompilerVersion"] = "1.3.2"
    extra["navigationVersion"] = "2.5.2"
    extra["roomVersion"] = "2.4.3"
    extra["hiltVersion"] = "2.44"
    extra["coroutinesVersion"] = "1.6.4"

    extra["javaVersion"] = JavaVersion.VERSION_11
    extra["jvmTargetVersion"] = "11"

    extra["jUnitVersion"] = "4.13.2"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}