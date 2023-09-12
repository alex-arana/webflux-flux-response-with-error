import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

val armeriaVersion: String by extra
val nettyVersion: String by extra
val reactorKotlinExtensionsVersion: String by extra
val springBootVersion: String by extra

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("com.linecorp.armeria:armeria-bom:$armeriaVersion"))
    implementation(platform("io.netty:netty-bom:$nettyVersion"))
    implementation("com.linecorp.armeria:armeria-spring-boot2-webflux-starter")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:$reactorKotlinExtensionsVersion")

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
