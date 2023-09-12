import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

val armeriaVersion: String by extra
val nettyVersion: String by extra
val kotestJUnitVersion: String by extra
val kotestSpringVersion: String by extra
val reactorKotlinExtensionsVersion: String by extra
val springBootVersion: String by extra

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("com.linecorp.armeria:armeria-bom:$armeriaVersion"))
    implementation(platform("io.netty:netty-bom:$nettyVersion"))
    implementation("com.linecorp.armeria:armeria-spring-boot3-webflux-starter")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:$reactorKotlinExtensionsVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestJUnitVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
}

tasks {
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            languageVersion.set(KotlinVersion.KOTLIN_1_9)
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
            showCauses = false
            showExceptions = false
            showStackTraces = false
        }
    }
}
