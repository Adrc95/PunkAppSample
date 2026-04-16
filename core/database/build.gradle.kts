plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.adrc95.core.database"
    compileSdk {
        version = release(37)
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
}
dependencies {
    implementation(project(":core:common"))
    implementation(project(":feature:beers:domain"))
    implementation(project(":feature:beers:data"))
    implementation(libs.arrow.core)
    implementation(libs.inject.javax.inject)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.kotlin.serialization.json)
    ksp(libs.room.compiler)
}
