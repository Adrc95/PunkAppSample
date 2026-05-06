plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.adrc95.core.common"
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
    implementation(libs.arrow.core)
    implementation(libs.retrofit.core)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
}
