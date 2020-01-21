import org.gradle.jvm.tasks.Jar
import java.net.URI

val commonsCompressVersion: String by project
val junitJupiterVersion: String by project
val fileWorkerVersion: String by project
val xzVersion: String by project
val mysticCryptVersion: String by project

plugins {
    signing
    jacoco
    kotlin("jvm") version "1.3.61"
    id("java")
    id("maven-publish")
    id("com.github.ben-manes.versions") version "0.27.0"
    id("com.github.hierynomus.license") version "0.15.0"
    id("org.jetbrains.dokka") version "0.10.0"
}

group = "de.alpharogroup"
version = "1-SNAPSHOT"
description = "zip-worker"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.commons:commons-compress:$commonsCompressVersion")
    implementation("org.tukaani:xz:$xzVersion")
    implementation("de.alpharogroup:file-worker:$fileWorkerVersion")
    testImplementation("de.alpharogroup:mystic-crypt:$mysticCryptVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}

// Configure existing Dokka task to output HTML to typical Javadoc directory
tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

// Create dokka Jar task from dokka task output
val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    // dependsOn(tasks.dokka) not needed; dependency automatically inferred by from(tasks.dokka)
    from(tasks.dokka)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(dokkaJar)

            pom {
                name.set(rootProject.name)
                url.set("https://github.com/astrapi69/" + rootProject.name)
                description.set("Extension methods for zipping files in 7z-format and extract")
                organization {
                    name.set("Alpha Ro Group UG (haftungsbeschrängt)")
                    url.set("http://www.alpharogroup.de/")
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/astrapi69/" + rootProject.name + "/issues")
                }
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("astrapi69")
                        name.set("Asterios Raptis")
                    }
                }
                scm {
                    connection.set("scm:git:git:@github.com:astrapi69/" + rootProject.name + ".git")
                    developerConnection.set("scm:git:git@github.com:astrapi69/" + rootProject.name + ".git")
                    url.set("git:@github.com:astrapi69/" + rootProject.name + ".git")
                }
            }

            repositories {
                maven {
                    credentials {
                        val usernameString = System.getenv("ossrhUsername")
                                ?: project.property("ossrhUsername")
                        val passwordString = System.getenv("ossrhPassword")
                                ?: project.property("ossrhPassword")
                        username = usernameString.toString()
                        password = passwordString.toString()
                    }
                    val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
                    val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots"
                    val projectVersion = version.toString()
                    val urlString = if (projectVersion.endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                    url = URI.create(urlString)
                }
            }
        }
    }
}

license {
    ext.set("year", "2020")
    ext.set("owner", "Asterios Raptis")
    header = rootProject.file("src/main/resources/LICENSE.txt")
    val value = listOf(
            "**/README",
            "**/README.md",
            "**/LICENSE",
            "**/NOTICE",
            "**/*.xml",
            "**/*.xsl",
            "**/*.xsd",
            "**/*.dtd",
            "**/*.html",
            "**/*.json",
            "**/*.jsp",
            "**/*.jpa",
            "**/*.sql",
            "**/*.properties",
            "**/*.bat",
            "**/*.gradle",
            "**/*.MF",
            "**/*.txt",
            "**/*.vm",
            "**/*.log",
            "**/*.map",
            "**/*.js.map",
            "**/*.tmpl",
            "**/*.js.tmpl",
            "**/*.editorconfig",
            "src/test/resources/**",
            "src/main/resources/**",
            "out/**",
            "build/**"
    )
    excludes(value)
}

// workhack for license issue #76
gradle.startParameter.excludedTaskNames += "licenseMain"
gradle.startParameter.excludedTaskNames += "licenseTest"

signing {
    sign(publishing.publications["mavenJava"])
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging{
        events("passed", "skipped", "failed")
    }
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = false
        csv.isEnabled = false
        html.isEnabled = true
        html.destination = file("$buildDir/reports/coverage")
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.0".toBigDecimal()
            }
        }
    }
}

val testCoverage by tasks.registering {
    group = "verification"
    description = "Runs the unit tests with coverage."

    dependsOn(":test", ":jacocoTestReport", ":jacocoTestCoverageVerification")
    val jacocoTestReport = tasks.findByName("jacocoTestReport")
    jacocoTestReport?.mustRunAfter(tasks.findByName("test"))
    tasks.findByName("jacocoTestCoverageVerification")?.mustRunAfter(jacocoTestReport)
}