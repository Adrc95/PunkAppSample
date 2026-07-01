plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.navigation.safe.args) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply false
}

apply(from = "$rootDir/gradle-scripts/detekt.gradle.kts")

tasks.register("unitTestAll") {
    group = "verification"
    description = "Runs all unit tests in Android and JVM modules"
}

subprojects {
    tasks.matching { it.name == "testDebugUnitTest" || it.name == "test" }.configureEach {
        rootProject.tasks.named("unitTestAll") {
            dependsOn(this@configureEach)
        }
    }
}
