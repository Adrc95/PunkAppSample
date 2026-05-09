import com.adrc95.punkappsample.addAndroidTestDependencies
import com.adrc95.punkappsample.addUnitTestDependencies
import com.adrc95.punkappsample.libs
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeaturePresentationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("punkapp.android.library")
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
            }
            extensions.configure<LibraryExtension> {
                buildFeatures {
                    viewBinding = true
                }
            }
            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", project(":core:ui"))
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel").get())
                add("implementation", libs.findLibrary("androidx.core.ktx").get())
                add("implementation", libs.findLibrary("androidx.appcompat").get())
                add("implementation", libs.findLibrary("androidx.material").get())
                add("implementation", libs.findLibrary("androidx.constraintlayout").get())
                add("implementation", libs.findLibrary("androidx.navigation.ui").get())
                add("implementation", libs.findLibrary("androidx.navigation.fragment").get())
                add("implementation", libs.findLibrary("androidx.recyclerview").get())
                add("implementation", libs.findLibrary("arrow.core").get())
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.compiler").get())
                add("implementation", libs.findLibrary("hilt.lifecycle.viewmodel").get())
                add("implementation", libs.findLibrary("kotlin.coroutines.android").get())
                add("implementation", libs.findLibrary("shimmer").get())
                add("testImplementation", libs.findLibrary("arrow.core").get())
                add("testImplementation", libs.findLibrary("turbine").get())
            }
            addAndroidTestDependencies()
            addUnitTestDependencies()
        }
    }
}
