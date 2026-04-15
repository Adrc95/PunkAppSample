plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation.safe.args)
}
android {
    namespace = "com.adrc95.punkappsample"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.adrc95.punkappsample"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    lint {
        abortOnError = false
        disable += listOf(
            "UnsafeExperimentalUsageError",
            "UnsafeExperimentalUsageWarning"
        )
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    //KOTLIN
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.serialization.json)
    // ANDROID
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.recyclerview )
    implementation(libs.androidx.swiperefreshlayout)
    // DAGGER HILT
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.lifecycle.viewmodel)
    //ARROW
    implementation(libs.arrow.core)
    //RETROFIT
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    //GLIDE
    implementation(libs.glide.core)
    ksp(libs.glide.compiler)
    //ROOM
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    //SHIMMER
    implementation(libs.shimmer)
}
