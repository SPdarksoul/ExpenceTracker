// Top-level build.gradle file

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add classpath dependencies for plugins
        classpath (libs.hilt.android.gradle.plugin)
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    id("com.google.gms.google-services") version "4.4.2" apply false

}

allprojects {
    repositories {

    }
}
