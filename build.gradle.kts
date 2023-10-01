plugins {
	java
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "com.test_task"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}

	all {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.apache.logging.log4j:log4j-spring-boot")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	implementation("org.flywaydb:flyway-core")

	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")


	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:postgresql:1.19.0")
	testImplementation("org.testcontainers:testcontainers:1.19.0")
	testImplementation("org.testcontainers:junit-jupiter:1.19.0")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
