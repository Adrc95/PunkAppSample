plugins {
    id("punkapp.android.feature.presentation")
}

android {
    namespace = "com.adrc95.feature.beers.presentation"
}

dependencies {
    implementation(libs.androidx.swiperefreshlayout)
    implementation(project(":feature:beers:domain"))
    testImplementation(project(":feature:beers:testing"))
}
