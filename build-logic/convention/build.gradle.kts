plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("kotlinLibrary") {
            id = "punkapp.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
        register("androidApplication") {
            id = "punkapp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "punkapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeatureDomain") {
            id = "punkapp.android.feature.domain"
            implementationClass = "AndroidFeatureDomainConventionPlugin"
        }
        register("androidFeatureData") {
            id = "punkapp.android.feature.data"
            implementationClass = "AndroidFeatureDataConventionPlugin"
        }
        register("androidFeaturePresentation") {
            id = "punkapp.android.feature.presentation"
            implementationClass = "AndroidFeaturePresentationConventionPlugin"
        }
        register("database") {
            id = "punkapp.database"
            implementationClass = "DatabaseConventionPlugin"
        }
        register("network") {
            id = "punkapp.network"
            implementationClass = "NetworkConventionPlugin"
        }
    }
}
