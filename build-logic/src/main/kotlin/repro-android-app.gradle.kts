@file:Suppress("UnstableApiUsage")

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

val libs = the<LibrariesForLibs>()

@Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")
plugins {
  id("com.android.application")
  id("org.gradle.android.cache-fix")
  id("repro-kotlin")
}

android {
  compileSdk = libs.versions.android.sdk.compile.get().toInt()
  defaultConfig {
    targetSdk = libs.versions.android.sdk.target.get().toInt()
    minSdk = libs.versions.android.sdk.min.get().toInt()

    applicationId = "com.eygraber.repro"

    versionCode = 1
    versionName = "1.0.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    multiDexEnabled = true
  }

  signingConfigs {
    named("debug") {
      storeFile = file("debug.keystore")
    }

    register("internal") {
      keyAlias = "internal"
      keyPassword = "Password1234"
      storeFile = file("internal.keystore")
      storePassword = "Password1234"
    }

    register("prod") {
      keyAlias = "prod"
      keyPassword = "Password1234"
      storeFile = file("prod.keystore")
      storePassword = "Password1234"
    }
  }

  buildTypes {
    named("release") {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles.clear()
      proguardFiles += project.file("proguard-rules.pro")
    }

    named("debug") {
      // overwrite the default signingConfig for debug builds (https://stackoverflow.com/a/29618981/691639)
      signingConfig = signingConfigs.named("internal").get()

      // always gets applied after the flavor's applicationIdSuffix
      applicationIdSuffix = ".debug"

      versionNameSuffix = "-DEBUG"

      isMinifyEnabled = false
    }
  }

  flavorDimensions += "environment"

  productFlavors {
    register("dev") {
      dimension = "environment"

      applicationIdSuffix = ".dev"

      signingConfig = signingConfigs.named("internal").get()
    }

    register("prod") {
      dimension = "environment"

      signingConfig = signingConfigs.named("prod").get()
    }
  }

  lint {
    checkDependencies = true
    checkReleaseBuilds = false
    disable += listOf(
      "UnusedResources",
      "LintError",
      "ParcelCreator",
      "DalvikOverride",
      "InvalidPackage",
      "VulnerableCordovaVersion",
      "MissingTranslation",
      "Typos"
    )
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
    targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get())
  }

  packagingOptions {
    resources.pickFirsts += "META-INF/*"
  }

  buildFeatures {
    buildConfig = true
  }

  dependenciesInfo {
    includeInBundle = false
    includeInApk = false
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

dependencies {
  implementation(libs.google.play.core)
}
