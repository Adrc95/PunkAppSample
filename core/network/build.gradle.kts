plugins {
    id("punkapp.network")
}

android {
    namespace = "com.adrc95.core.network"
}

dependencies {
    implementation(project(":feature:beers:domain"))
    implementation(project(":feature:beers:data"))
    testImplementation(project(":feature:beers:testing"))
}
