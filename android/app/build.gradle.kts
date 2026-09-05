plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val geckoViewVersion: String = (project.findProperty("geckoViewVersion") as String?)
    ?: "133.0.20241111180444"

android {
    namespace = "org.afriverse.browser"
    compileSdk = 37

    defaultConfig {
        applicationId = "org.afriverse.browser"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0-alpha"
        resValue("string", "app_name", "Afriverse Browser")
        buildConfigField("String", "HOMEPAGE_URL", "\"https://alpha.afriversedao.org\"")
    }

    signingConfigs {
        create("release") {
            val keystorePath = System.getenv("AFRIVERSE_KEYSTORE_PATH")
            if (keystorePath != null) {
                storeFile = file(keystorePath)
                storePassword = System.getenv("AFRIVERSE_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("AFRIVERSE_KEY_ALIAS")
                keyPassword = System.getenv("AFRIVERSE_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            val keystorePath = System.getenv("AFRIVERSE_KEYSTORE_PATH")
            signingConfig = if (keystorePath != null) {
                signingConfigs.getByName("release")
            } else {
                signingConfigs.getByName("debug")
            }
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity-ktx:1.9.2")

    implementation("org.mozilla.geckoview:geckoview:$geckoViewVersion")
}
