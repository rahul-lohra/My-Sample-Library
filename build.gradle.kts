// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
}

tasks.register("updateVersionProperties") {
    doLast {
        val propertiesFile = file("gradle.properties")

        if (propertiesFile.exists()) {
            val properties = java.util.Properties().apply {
                propertiesFile.inputStream().use { load(it) }
            }

            // Update values based on passed arguments
            val newVersionName = project.findProperty("newVersionName")?.toString()
            val newVersionCode = project.findProperty("newVersionCode")?.toString()

            newVersionName?.let { properties["VERSION_NAME"] = it }
            newVersionCode?.let { properties["VERSION_CODE"] = it }

            // Write back to file
            propertiesFile.writer().use { properties.store(it, null) }

            println("✅ gradle.properties updated successfully!")
            println("VERSION_NAME=${properties["VERSION_NAME"]}")
            println("VERSION_CODE=${properties["VERSION_CODE"]}")
        } else {
            println("⚠️ gradle.properties file not found!")
        }
    }
}
