plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id ("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.task.expencetracker"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.task.expencetracker"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled =true
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
    kapt {
        correctErrorTypes = true
    }
    buildFeatures {

        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Update to a stable version if necessary
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx.v1120)
    implementation(libs.androidx.lifecycle.runtime.ktx.v261)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom)) // Replace with the latest version

    implementation(libs.ui) // Adjust versions if necessary
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview) // Update to match Compose BOM
    implementation (libs.androidx.material3)
    implementation(libs.androidx.navigation.compose.v270)
    implementation(libs.androidx.runtime.livedata.v160)
    implementation(libs.protobuf.javalite)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.androidx.core.i18n)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.ui.test.android)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.play.services.auth)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(platform(libs.androidx.compose.bom.v202410))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation (libs.hilt.android.v249)
    kapt (libs.hilt.android.compiler.v2461)
    implementation(libs.dagger.hilt.android)
    kapt (libs.google.hilt.android.compiler)
    implementation (libs.androidx.hilt.navigation.compose)
    kapt (libs.androidx.hilt.compiler)
        // AndroidX and Jetpack Compose Libraries
        implementation(libs.androidx.lifecycle.runtime.compose)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.constraintlayout)
    implementation (libs.androidx.runtime) // Check for the latest version
    val room_version = "2.6.1"
    // Room
    implementation(libs.androidx.room.runtime)
    kapt("androidx.room:room-compiler:$room_version")
    implementation(libs.androidx.room.ktx)

    // MPAndroidChart
    implementation(libs.mpandroidchart)

    implementation (libs.androidx.multidex)
    implementation ("androidx.compose.foundation:foundation:1.5.0") // Or the latest stable version
    implementation (libs.accompanist.swiperefresh.v0280)
    implementation (libs.androidx.ui.v150)

//fireBase
    implementation("io.coil-kt:coil-compose:2.0.0")
    implementation(platform(libs.firebase.bom))
}
