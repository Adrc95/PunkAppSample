import com.adrc95.punkappsample.addAndroidTestDependencies
import com.adrc95.punkappsample.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DatabaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("punkapp.android.library")
                apply("com.google.devtools.ksp")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", libs.findLibrary("arrow.core").get())
                add("implementation", libs.findLibrary("inject.javax.inject").get())
                add("implementation", libs.findLibrary("kotlin.serialization.json").get())
                add("implementation", libs.findLibrary("room.ktx").get())
                add("implementation", libs.findLibrary("room.runtime").get())
                add("ksp", libs.findLibrary("room.compiler").get())
                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("room.testing").get())
                add("androidTestImplementation", libs.findLibrary("turbine").get())
            }
            addAndroidTestDependencies()
        }
    }
}
