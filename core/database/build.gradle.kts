plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.adrc95.core.database"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
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

    sourceSets {
        getByName("test").java.srcDir("src/sharedTest/java")
        getByName("androidTest").java.srcDir("src/sharedTest/java")
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
    testImplementation(libs.junit)
    testImplementation(project(":feature:beers:testing"))
    androidTestImplementation(libs.kotlin.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.room.testing)
    androidTestImplementation(libs.turbine)
}
