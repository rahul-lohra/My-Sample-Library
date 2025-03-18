plugins {
//    alias(libs.plugins.android.application)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven.publish)

}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.example.samplelibrary" // Replace with your group ID
            artifactId = "sample-library" // Replace with your artifact ID
            version = "1.0.0" // Initial version

            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        maven {
            url = uri("$buildDir/repo") // Local repo for testing
        }
    }
}

tasks.register("updateVersion") {
    doLast {
        val version = project.version
        val file = file("app/build.gradle")
        val content = file.readText().replace("version = '$version'", "version = '${System.getenv("NEW_VERSION")}'")
        file.writeText(content)
    }
}
//android {
//    namespace = "rahullohra.mysamplelibrary"
//    compileSdk = 35
//
//    defaultConfig {
//        applicationId = "rahullohra.mysamplelibrary"
//        minSdk = 24
//        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}