plugins {
    id("punkapp.android.application")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation.safe.args)
}

android {
    namespace = "com.adrc95.punkappsample"

    defaultConfig {
        applicationId = "com.adrc95.punkappsample"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.adrc95.punkappsample.HiltTestRunner"
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

    sourceSets {
        getByName("androidTest").assets.srcDir("../core/network/src/test/resources")
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":feature:beers:presentation"))
    implementation(project(":feature:beers:domain"))
    implementation(project(":feature:beers:data"))
    //KOTLIN
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.kotlin.serialization.json)
    // ANDROID
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)
    // DAGGER HILT
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.lifecycle.viewmodel)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.room.runtime)
    androidTestImplementation(libs.arrow.core)
    androidTestImplementation(libs.kotlin.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.mockwebserver)
}
