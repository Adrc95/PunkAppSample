plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.adrc95.core.network"
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
    implementation(libs.kotlin.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    testImplementation(libs.junit)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.kotlin.coroutines.test)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.kotlin.coroutines.test)
    testImplementation(project(":feature:beers:testing"))
}
