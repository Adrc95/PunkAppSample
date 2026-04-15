plugins {
    id 'java-library'
    id 'kotlin'
}

dependencies {
    implementation(project(Modules.domain))
    implementation(arrow.core)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}