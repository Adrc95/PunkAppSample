package com.adrc95.punkappsample

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

private fun Project.lib(alias: String) = libs.findLibrary(alias).get()

fun Project.addUnitTestDependencies() {
    dependencies {
        add("testImplementation", lib("junit"))
        add("testImplementation", lib("kotlin.coroutines.test"))
        add("testImplementation", lib("io.mockk"))
    }
}

fun Project.addAndroidTestDependencies() {
    dependencies {
        add("androidTestImplementation", lib("androidx.junit"))
        add("androidTestImplementation", lib("androidx.test.runner"))
        add("androidTestImplementation", lib("kotlin.coroutines.test"))
    }
}
