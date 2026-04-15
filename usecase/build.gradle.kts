plugins {
    alias(libs.plugins.android.library)
    id 'kotlin'
}

dependencies {
    implementation(project(Modules.data))
    implementation(project(Modules.domain))
    testImplementation(libs.junit)
    implementation(arrow.core)
    implementation(kotlin.coroutines.android)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}