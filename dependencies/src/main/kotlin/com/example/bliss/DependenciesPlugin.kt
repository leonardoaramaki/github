package com.example.bliss

import org.gradle.api.Plugin
import org.gradle.api.Project

class DependenciesPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        // No-op
    }
}
object Versions {
    const val COMPILE_SDK = 30
    const val MIN_SDK = 21
    const val TARGET_SDK = 30
    const val BUILD_TOOLS = "30.0.2"
}

object Deps {
    const val androidXAppCompat = "androidx.appcompat:appcompat:1.2.0"
    const val androidXCore = "androidx.core:core-ktx:1.3.2"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
    const val junit5 = "org.junit.jupiter:junit-jupiter:5.6.3"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:1.4.10"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
    const val material = "com.google.android.material:material:1.2.1"
    const val moshi = "com.squareup.moshi:moshi:1.11.0"
    const val okHttp = "com.squareup.okhttp3:okhttp:4.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val room = "androidx.room:room-runtime:2.2.6"
    const val roomKtx = "androidx.room:room-ktx:2.2.6"
    const val roomCompiler = "androidx.room:room-compiler:2.2.6"
    const val truth = "com.google.truth:truth:1.1.1"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    const val workManager = "androidx.work:work-runtime-ktx:2.4.0"
}