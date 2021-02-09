plugins {
  id("org.springframework.boot") version "2.4.1"
  id("io.spring.dependency-management") version "1.0.10.RELEASE"
  kotlin("jvm") version "1.4.21"
  kotlin("plugin.spring") version "1.4.21"
  kotlin("kapt") version "1.4.21"
}

tasks.jar { enabled = false }
tasks.bootJar { enabled = false }

ext["protostuff.version"] = "1.0.10"

allprojects {
  repositories {
    mavenLocal()
    mavenCentral()
  }
}

subprojects {
  apply {
    plugin("java")
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
    plugin("org.jetbrains.kotlin.jvm")
    plugin("org.jetbrains.kotlin.plugin.spring")
    plugin("org.jetbrains.kotlin.kapt")
  }

  group = "com.dqpi"
  version = "0.0.1-SNAPSHOT"
  java.sourceCompatibility = JavaVersion.VERSION_1_8

  dependencyManagement {
    dependencies {
      dependency("com.dyuproject.protostuff:protostuff-core:${property("protostuff.version")}")
      dependency("com.dyuproject.protostuff:protostuff-runtime:${property("protostuff.version")}")
    }
  }

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
      jvmTarget = "1.8"
    }
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }
}
