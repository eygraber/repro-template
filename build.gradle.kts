import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// https://youtrack.jetbrains.com/issue/IDEA-262280
@Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")
plugins {
  // need to have kotlin and android here because of a restriction in the Kotlin plugin
  // to only be loaded once
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.android.application) apply false

  alias(libs.plugins.versionsBenManes)

  id("repro-validate-gradle-properties")
}

tasks.register<Delete>("clean") {
  delete(buildDir)
}
