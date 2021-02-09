tasks.jar { enabled = true }
tasks.bootJar { enabled = false }

dependencies {
  api("org.springframework.boot:spring-boot-starter-rsocket")
  api("com.dyuproject.protostuff:protostuff-core")
  api("com.dyuproject.protostuff:protostuff-runtime")
  api("io.projectreactor.kotlin:reactor-kotlin-extensions")
  api("org.jetbrains.kotlin:kotlin-reflect")
  api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  api("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
}