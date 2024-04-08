plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.androidDaggerHilt)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.qualflow.verifyclean"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.qualflow.verifyclean"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.lifecycle.runtime.compose)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    /*Dagger Hilt*/
    val hiltVersion = "2.48"
    implementation(libs.dagger.hilt)
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    /*Kotlin Serialization*/
    implementation(libs.ktx.serialization)

    /*Accompanist Permission Handling*/
    implementation(libs.accompanist.permissions)

    /*viewmodel*/
    implementation(libs.compose.viewmodel)
    implementation(libs.ktx.viewmodel)
    implementation(libs.dagger.hilt.navigation)

    /*compose navigation*/
    implementation(libs.compose.navigation)
}