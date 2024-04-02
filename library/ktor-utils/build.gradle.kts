plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.weredev.ktorUtils"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.12.0")
//    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
//    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
//    implementation ("com.squareup.okhttp3:okhttp:4.12.0")

    implementation ("io.ktor:ktor-client-core:2.3.7")


}

//ktor-client-auth = { module = "io.ktor:ktor-client-auth", version.ref = "ktor" }

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.weredevelopers"
                artifactId = "ktorUtils"
                version = "1.0"
            }
        }
    }
}