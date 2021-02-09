tasks.jar { enabled = true }
tasks.bootJar { enabled = false }

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("io.projectreactor:reactor-core")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

