import java.util.Properties

plugins {
//    alias(libs.plugins.android.application)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven.publish)

}

fun getLocalProperty(propertyName: String): String? {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        val properties = Properties()
        localPropertiesFile.inputStream().use { properties.load(it) }
        return properties.getProperty(propertyName)
    }
    return null
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.rahullohra" // Replace with your group ID (e.g., com.example)
            artifactId = "my-sample-library" // Replace with your artifact ID (e.g., my-sdk)
            version = project.properties["VERSION"] as String // Use the project's version

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name = "My Sample Library" // Replace with your SDK name
                description = "This library is pushed by using semantic release" // Replace with your SDK description
                url = "https://github.com/rahul-lohra/My-Sample-Library.git" // Replace with your project URL
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "rahul-lohra" // Replace with your developer ID
                        name = "Rahul Kumar Lohra" // Replace with your name
                        email = "tgunix@gmail.com" // Replace with your email
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/rahul-lohra/My-Sample-Library.git" // Replace with your Git URL
                    developerConnection = "scm:git:ssh://git@github.com/rahul-lohra/My-Sample-Library.git" // Or HTTPS
                    url = "https://github.com/rahul-lohra/My-Sample-Library"
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (project.version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = System.getenv("MAVEN_CENTRAL_USERNAME") ?: getLocalProperty("MAVEN_CENTRAL_USERNAME")
                password = System.getenv("MAVEN_CENTRAL_PASSWORD") ?: getLocalProperty("MAVEN_CENTRAL_PASSWORD")
            }
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
android {
    namespace = "rahullohra.mysamplelibrary"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            consumerProguardFile("proguard-android-optimize.txt")
            consumerProguardFile("proguard-rules.pro")
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}