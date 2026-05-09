plugins {
    id("punkapp.android.feature.domain")
}

android {
    namespace = "com.adrc95.feature.beers.data"
}

dependencies {
    implementation(project(":feature:beers:domain"))
}
