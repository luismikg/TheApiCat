import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.sql.delight)
    id("com.codingfeline.buildkonfig") version "0.17.1"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            //isStatic = true
            linkerOpts("-lsqlite3")
        }
    }

    jvm()

    sourceSets {
        task("testClasses")
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
            // AndroidX
            implementation(libs.androidx.startup.runtime)

            // SQLDelight
            implementation(libs.sql.delight.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.navigation.compose)

            //viewModel
            implementation(libs.viewmodel.compose)

            //material icon
            api(compose.materialIconsExtended)

            //koin dependency injection
            implementation(libs.koin.compose)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)

            //ktor internet
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.kotlin.serialization)

            //Coil load images from network
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            //Pager
            implementation(libs.paging.compose.common)
            implementation(libs.paging.common)

            // SQLDelight
            implementation(libs.sql.delight.common)
            api(libs.sql.delight.common.coroutines)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)

            // SQLDelight
            implementation(libs.sql.delight.ios)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.ktor.client.okhttp)

            // SQLDelight
            implementation(libs.sql.delight.desktop)
        }
    }
}

android {
    namespace = "com.bbb.thecatapi"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.bbb.thecatapi"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.bbb.thecatapi.MainKt"

        nativeDistributions {
            modules("java.sql")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "TheCatApp"
            packageVersion = "1.0.0"

            windows {
                iconFile.set(project.file("icon/windows_icon.ico"))
            }

            macOS {
                iconFile.set(project.file("icon/mac_icon.icns"))
            }

            linux {
                iconFile.set(project.file("icon/linux_icon.png"))
            }
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.bbb.data.database")
        }
    }
}

// see: https://medium.com/@uwaisalqadri/manage-project-environment-in-kotlin-multiplatform-mobile-528847c3bfc5
//Getting the variable of api_key from local.properties:
// the_cat_api_key = live_...
val localProperties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    FileInputStream(localPropertiesFile).use { input ->
        localProperties.load(input)
    }
}

val apiKey = localProperties.getProperty("the_cat_api_key") ?: "NO_API_KEY_FOUND"

buildkonfig {
    packageName = "com.bbb.thecatapi"

    defaultConfigs {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "THE_CAT_API_KEY",
            value = apiKey
        )
    }

    targetConfigs {
        create("androidMain") {}
        create("jvmMain") {}
        create("iosMain") {}
    }
}