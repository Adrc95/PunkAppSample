plugins {
    id("punkapp.database")
}

android {
    namespace = "com.adrc95.core.database"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets {
        getByName("test").kotlin.srcDir("src/sharedTest/java")
        getByName("androidTest").kotlin.srcDir("src/sharedTest/java")
    }
}

dependencies {
    implementation(project(":feature:beers:domain"))
    implementation(project(":feature:beers:data"))
    testImplementation(project(":feature:beers:testing"))
}
