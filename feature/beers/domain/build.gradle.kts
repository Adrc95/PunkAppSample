plugins {
    id("punkapp.android.feature.domain")
}

android {
    namespace = "com.adrc95.feature.beers.domain"
}

dependencies {
    testImplementation(project(":feature:beers:testing"))
}
