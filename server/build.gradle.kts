dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
  implementation(project(":protocol"))
  runtimeOnly("com.h2database:h2")
  runtimeOnly("io.r2dbc:r2dbc-h2")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")
  kapt("org.springframework.boot:spring-boot-configuration-processor")
}
