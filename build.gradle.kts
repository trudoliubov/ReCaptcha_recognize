import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val testNGVersion = "6.14.3"
val slf4jVersion = "1.6.1"


plugins {
    kotlin("jvm") version "1.5.21"
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation  ("org.seleniumhq.selenium", "selenium-java", "3.141.59")
    testImplementation("io.github.bonigarcia:webdrivermanager:4.4.3")
    implementation("org.testng", "testng", testNGVersion)
    implementation("org.slf4j", "slf4j-simple", slf4jVersion)
    implementation("com.ibm.watson", "speech-to-text", "9.2.0")

}

tasks.test {
    useTestNG()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}