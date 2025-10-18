import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.acms.whatnow"
    compileSdk = 36  // Use stable SDK

    // Load API keys from local.properties safely
    val localProperties = Properties()
    val localFile = rootProject.file("local.properties")
    if (localFile.exists()) {
        FileInputStream(localFile).use { localProperties.load(it) }
    }
    val newsApiKey: String = localProperties.getProperty("NEWS_API_KEY", "")
    val defaultWebClientId: String = localProperties.getProperty("DEFAULT_WEB_CLIENT_ID", "")

    defaultConfig {
        applicationId = "com.acms.whatnow"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Inject keys into BuildConfig
        buildConfigField("String", "NEWS_API_KEY", "\"$newsApiKey\"")
        buildConfigField("String", "DEFAULT_WEB_CLIENT_ID", "\"$defaultWebClientId\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.android.gms:play-services-auth:21.4.0")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Glide (images)
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
