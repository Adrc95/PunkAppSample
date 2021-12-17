
object Androidx {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.Androidx.appcompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.Androidx.constraintlayout}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Androidx.lifecycle}"
    const val material = "com.google.android.material:material:${Versions.Androidx.material}"
    const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.Androidx.navigation}"
    const val navigationFrag = "androidx.navigation:navigation-fragment-ktx:${Versions.Androidx.navigation}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.Androidx.recyclerview}"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.Androidx.swiperefreshlayout}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.Androidx.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.Androidx.room}"
    const val ktx = "androidx.room:room-ktx:${Versions.Androidx.room}"
}

object Hilt {
    const val android = "com.google.dagger:hilt-android:${Versions.dagger}"
    const val androidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.dagger}"
    const val lifecycleViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.Androidx.daggerHiltLifecycle}"
}

object Kotlin {
    const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}"
    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.coroutines}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.Kotlin.ktx}"
    const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Kotlin.serializationJson}"
}

object Arrow {
    const val core = "io.arrow-kt:arrow-core:${Versions.arrow}"
}

object Retrofit {
    const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val kotlinSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.Retrofit.kotlinSerializationConverter}"
    const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
}

object Glide {
    const val core = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object Shimmer {
    const val core = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
}

