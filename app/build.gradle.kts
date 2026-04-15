plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugin.navigation.safe.args)
}

android {
    namespace = "com.adrc95.punkapp"
    compileSdk AppConfig.compileSdkVersion

    defaultConfig {
        applicationId AppConfig.applicationId
        minSdk AppConfig.minSdkVersion
        targetSdk AppConfig.targetSdkVersion
        versionCode AppConfig.versionCode
        versionName AppConfig.versionName

        testInstrumentationRunner AppConfig.testInstrumentationRunner
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = '17'
    }

    buildFeatures {
        viewBinding true
    }

    lintOptions {
        abortOnError false
        // Because of annotation-experimental
        disable("UnsafeExperimentalUsageError", "UnsafeExperimentalUsageWarning")
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation project(Modules.data)
    implementation project(Modules.domain)
    implementation project(Modules.usecase)
    //KOTLIN
    implementation Kotlin.coroutineAndroid
    implementation Kotlin.coroutineCore
    implementation Kotlin.coreKtx
    implementation Kotlin.serializationJson
    // ANDROID
    implementation Androidx.appCompat
    implementation Androidx.material
    implementation Androidx.constraintLayout
    implementation Androidx.lifecycleViewModel
    implementation Androidx.navigation
    implementation Androidx.navigationFrag
    implementation Androidx.recyclerView
    implementation Androidx.swipeRefreshLayout
    // DAGGER HILT
    implementation Hilt.android
    kapt Hilt.androidCompiler
    implementation Hilt.lifecycleViewModel
    //ARROW
    implementation Arrow.core
    //RETROFIT
    implementation Retrofit.core
    implementation Retrofit.kotlinSerialization
    implementation Retrofit.httpLoggingInterceptor
    //GLIDE
    implementation Glide.core
    kapt Glide.compiler
    //ROOM
    implementation Room.runtime
    kapt Room.compiler
    //SHIMMER
    implementation Shimmer.core
}