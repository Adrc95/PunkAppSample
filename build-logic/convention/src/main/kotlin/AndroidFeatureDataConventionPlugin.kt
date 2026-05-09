

import com.adrc95.punkappsample.addUnitTestDependencies
import com.adrc95.punkappsample.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureDataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("punkapp.android.library")
            }
            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", libs.findLibrary("arrow.core").get())
                add("implementation", libs.findLibrary("kotlin.coroutines.core").get())
                add("implementation", libs.findLibrary("inject.javax.inject").get())
                add("testImplementation", libs.findLibrary("arrow.core").get())
            }
            addUnitTestDependencies()
        }
    }
}
