@file:Suppress("UnstableApiUsage")

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

val libs = the<LibrariesForLibs>()

// can't use libs in a precompiled script plugin block
plugins {
  id("com.android.library")
  id("org.gradle.android.cache-fix")
}

android {
  compileSdk = libs.versions.android.sdk.compile.get().toInt()

  defaultConfig {
    consumerProguardFile(project.file("consumer-rules.pro"))

    minSdk = libs.versions.android.sdk.min.get().toInt()

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
  }

  packagingOptions {
    resources.pickFirsts += "META-INF/*"
  }

  buildTypes {
    named("release") {
      isMinifyEnabled = false
    }
    named("debug") {
      isMinifyEnabled = false
    }
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }

  flavorDimensions += "environment"

  productFlavors.register("dev") {
    dimension = "environment"
  }

  productFlavors.register("prod") {
    dimension = "environment"
  }

  dependencies {
    coreLibraryDesugaring(libs.android.desugar)
  }
}

androidComponents {
  beforeVariants(selector().withFlavor("environment" to "prod")) { variant ->
    variant.enable = false
  }
}
