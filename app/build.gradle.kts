plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.firstapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.firstapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    dependencies {
        // OSMDroid for OpenStreetMap
        implementation("org.osmdroid:osmdroid-android:6.1.16")

        // Optional: For additional features like markers, overlays, etc.
        implementation("org.osmdroid:osmdroid-mapsforge:6.1.16")
        implementation("org.osmdroid:osmdroid-geopackage:6.1.16")
        // Retrofit
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")

        // Coil (for image loading, if needed)
        implementation("io.coil-kt:coil-compose:2.5.0")

        // ViewModel
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

        // Navigation (keeping the most recent version)
        implementation("androidx.navigation:navigation-compose:2.8.5")

        // Material 3 (keeping the most recent version)
        implementation("androidx.compose.material3:material3:1.3.1")

        // Compose UI (keeping the most recent version)
        implementation("androidx.compose.ui:ui:1.7.6")
        implementation("androidx.compose.foundation:foundation:1.7.6")
        implementation("androidx.compose.foundation:foundation-layout:1.7.6")

        implementation("com.google.android.libraries.places:places:3.3.0")

        // Other dependencies
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
        implementation(libs.androidx.appcompat)
        implementation(libs.play.services.location)
    }
}