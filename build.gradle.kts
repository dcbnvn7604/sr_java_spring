plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.sr"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter:3.4.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.4")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.4")
	implementation("org.springframework.boot:spring-boot-starter-webflux:3.4.4")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:3.4.4")
	compileOnly("org.projectlombok:lombok:1.18.38")
	annotationProcessor("org.projectlombok:lombok:1.18.38")
	implementation("org.postgresql:r2dbc-postgresql:1.0.7.RELEASE")
	implementation("org.postgresql:postgresql:42.7.5")
	implementation("org.springframework.boot:spring-boot-starter-security:3.4.4")
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
	testCompileOnly("org.projectlombok:lombok:1.18.38")
	testAnnotationProcessor("org.projectlombok:lombok:1.18.38")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
