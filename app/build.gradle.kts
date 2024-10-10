plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.potatomeme.android_ui_test"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.potatomeme.android_ui_test"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(":custom-ui:sample"))
    implementation(project(":custom-ui:chart"))
    implementation(project(":screen:planfit"))
    //tba - ticket booking app
    implementation(project(":screen:ticket-booking-app:presentation"))
    implementation(project(":screen:ticket-booking-app:domain"))
    implementation(project(":screen:ticket-booking-app:data"))
    //cip - cat image provider
    implementation(project(":screen:cat-image-provider:presentation"))
    implementation(project(":screen:cat-image-provider:domain"))
    implementation(project(":screen:cat-image-provider:data"))
    //cna - chirang note app
    implementation(project(":screen:chirang-note-app:presentation-xml"))
    implementation(project(":screen:chirang-note-app:domain"))
    implementation(project(":screen:chirang-note-app:data"))
    //compose-basic
    implementation(project(":screen:clone-compose-codelabs:compose-basic"))
    //compose-basic-layout
    implementation(project(":screen:clone-compose-codelabs:compose-basic-layout"))
    //compose-state
    implementation(project(":screen:clone-compose-codelabs:compose-state"))

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

    //hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    //navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    //firebase
    implementation(libs.firebase.database)
}