import org.gradle.api.Project
import org.gradle.kotlin.dsl.withGroovyBuilder

fun Project.configureDetekt() {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    extensions.findByName("detekt")?.withGroovyBuilder {
        setProperty("buildUponDefaultConfig", true)
        setProperty("allRules", false)
        setProperty("parallel", true)
        setProperty("autoCorrect", false)
        setProperty("basePath", rootDir.absolutePath)
        setProperty("baseline", file("$projectDir/detekt-baseline.xml"))

        setProperty("config", files("$rootDir/config/detekt.yml"))
    }

    tasks.matching { it.name.startsWith("detekt") }.configureEach {
        withGroovyBuilder {
            "setSource"(
                files(
                    "src/main/java",
                    "src/main/kotlin",
                    "src/test/java",
                    "src/test/kotlin",
                    "src/androidTest/java",
                    "src/androidTest/kotlin",
                    "src/sharedTest/java",
                    "src/sharedTest/kotlin"
                )
            )
            setProperty("jvmTarget", "17")
            "reports" {
                "html" {
                    setProperty("required", true)
                }
                "xml" {
                    setProperty("required", true)
                }
                "txt" {
                    setProperty("required", false)
                }
                "sarif" {
                    setProperty("required", true)
                }
                "md" {
                    setProperty("required", false)
                }
            }
        }
    }
}

subprojects {
    pluginManager.withPlugin("com.android.application") {
        configureDetekt()
    }
    pluginManager.withPlugin("com.android.library") {
        configureDetekt()
    }
}
