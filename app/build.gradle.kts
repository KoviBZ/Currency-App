plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
}
//    id("kotlin-android-extensions")

android {
    namespace = "com.currencyapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.currencyapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        buildConfigField("String", "BASE_URL", "\"https://hiring.revolut.codes/api/android/\"")

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            signingConfig = signingConfigs.release
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("release_keystore.jks")
            storePassword = "WPyF3oS0XB"
            keyAlias = "currency_keystore"
            keyPassword = "WPyF3oS0XB"
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

dependencies {
    val version_lifecycle = "2.8.7"
    val version_compose = "1.7.6"
    val version_coil = "3.0.4"
    val version_koin = "3.2.2"
    val version_coroutines = "1.9.0"

    //Currency picker
//    implementation("com.github.midorikocak:currency-picker-android:1.2.1")

    //Circle image view
//    implementation "de.hdodenhof:circleimageview:$circle_image_version"

    //TODO remove
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    //TODO EO remove

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.appcompat:appcompat:1.7.0")

    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.material:material:$version_compose")
    implementation("androidx.compose.ui:ui:$version_compose")
    implementation("androidx.compose.ui:ui-graphics:$version_compose")
    implementation("androidx.compose.ui:ui-tooling-preview:$version_compose")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$version_lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$version_lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$version_lifecycle")

    implementation("io.coil-kt.coil3:coil-compose:$version_coil")
    implementation("io.coil-kt.coil3:coil-network-okhttp:$version_coil")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.2.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    implementation("io.insert-koin:koin-core:$version_koin")
    implementation("io.insert-koin:koin-android:$version_koin")
    implementation("io.insert-koin:koin-androidx-compose:$version_koin")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$version_coroutines")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$version_coroutines")

    testImplementation("io.mockk:mockk:1.13.13")
    testImplementation("org.mockito:mockito-core:2.25.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
//    testImplementation("org.junit:junit-bom:5.10.0")
}