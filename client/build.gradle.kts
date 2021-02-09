dependencies {
  implementation("org.springframework.boot:spring-boot-starter-rsocket")
  implementation(project(":protocol"))
  implementation(project(":ui"))
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")
  kapt("org.springframework.boot:spring-boot-configuration-processor")
}


