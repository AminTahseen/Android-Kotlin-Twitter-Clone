import org.jetbrains.kotlin.gradle.utils.loadPropertyFromResources
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("kotlin-android")
}

android {
    namespace = "com.example.threadssocialmediaapp"
    compileSdk = 35

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    val apiId = properties.getProperty("APP_ID")

    defaultConfig {
        applicationId = "com.example.threadssocialmediaapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "APP_KEY", "\"${apiId}\"")
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
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    // dagger hilt
    val daggerHilt = "2.46"
    implementation("com.google.dagger:hilt-android:$daggerHilt")
    kapt("com.google.dagger:hilt-compiler:$daggerHilt")

    // moshi
    val moshi = "1.15.0"
    implementation("com.squareup.moshi:moshi:$moshi")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshi")
    implementation("com.squareup.moshi:moshi-kotlin:$moshi")

    // Fragment lifecycle methods
    implementation("androidx.fragment:fragment-ktx:1.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

    // Navigation component
    val nav_version_ktx = "2.1.0-beta01"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version_ktx")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version_ktx")

    // retrofit & GSON
    val retrofit = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit")

    // coroutine
    val coroutine = "1.5.2"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine")

    // circle imageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // handle datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.0")

    // circular progress drawable
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // paging 3 pagination
    implementation("androidx.paging:paging-runtime:3.0.0")
    implementation("com.github.markomilos:paginate:1.0.0")

    // room database
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    androidTestImplementation("androidx.room:room-testing:$roomVersion")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
