import kotlin.math.sign

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin library project to get you started.
 */

plugins {
  // Apply the Kotlin JVM plugin to add support for Kotlin.
  id("org.jetbrains.kotlin.jvm") version "1.3.72"

  // Apply the java-library plugin for API and implementation separation.
  `java-library`
  `maven-publish`
  antlr
  signing
  jacoco
  idea

  id("org.sonarqube") version "3.0"
  id("net.researchgate.release") version "2.8.1"
}

idea {
  module {
    generatedSourceDirs.add(file("${project.buildDir}/generated-src/antlr/main"))
  }
}

group = "io.github.oxisto"

repositories {
  // Use jcenter for resolving dependencies.
  // You can declare any Maven/Ivy/file repository here.
  jcenter()
}

dependencies {
  antlr("org.antlr:antlr4:4.8-1")

  // Align versions of all Kotlin components
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

  // Use the Kotlin JDK 8 standard library.
  api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  // Use the Kotlin test library.
  testImplementation("org.jetbrains.kotlin:kotlin-test")

  // Use the Kotlin JUnit integration.
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    implementation(kotlin("script-runtime"))
}

tasks.generateGrammarSource {
  outputDirectory = file("${project.buildDir}/generated-src/antlr/main/io/github/oxisto/reticulated/grammar")
  arguments = arguments + listOf("-visitor", "-package", "io.github.oxisto.reticulated.grammar")
}

tasks.named("compileKotlin") {
  dependsOn(":generateGrammarSource")
}

tasks.jacocoTestReport {
  reports {
    xml.isEnabled = true
  }
}

val mavenCentralUri: String
  get() {
    val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2"
    val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots"
    return if ((version as String).endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
  }

publishing {
  repositories {
    maven {
      url = uri("https://maven.pkg.github.com/oxisto/reticulated-python")

      credentials {
        val gitHubUsername: String? by project
        val gitHubToken: String? by project

        username = gitHubUsername
        password = gitHubToken
      }
    }

    maven {
      url = uri(mavenCentralUri)

      credentials {
        val mavenCentralUsername: String? by project
        val mavenCentralPassword: String? by project

        username = mavenCentralUsername
        password = mavenCentralPassword
      }
    }
  }

  publications {
    create<MavenPublication>("maven") {
      from(components["java"])
    }
  }
}

tasks.named("sonarqube") {
    dependsOn(":jacocoTestReport")
}

tasks.withType<GenerateModuleMetadata> {
  enabled = false
}

/*signing {
  val signingKey: String? by project
  val signingPassword: String? by project
  useInMemoryPgpKeys(signingKey, signingPassword)
  sign(publishing.publications["maven"])
}*/
