import com.adrc95.punkappsample.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class NetworkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("punkapp.android.library")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", libs.findLibrary("okhttp.logging").get())
                add("implementation", libs.findLibrary("retrofit.core").get())
                add("implementation", libs.findLibrary("retrofit.kotlin.serialization").get())
                add("implementation", libs.findLibrary("kotlin.serialization.json").get())
                add("implementation", libs.findLibrary("arrow.core").get())
                add("implementation", libs.findLibrary("inject.javax.inject").get())
                add("testImplementation", libs.findLibrary("junit").get())
                add("testImplementation", libs.findLibrary("mockwebserver").get())
                add("testImplementation", libs.findLibrary("kotlin.coroutines.test").get())
            }
        }
    }
}