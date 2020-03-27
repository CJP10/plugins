buildscript {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    checkstyle
}

apply<BootstrapPlugin>()
apply<VersionPlugin>()

allprojects {
    group = "com.openosrs"
    version = ProjectVersions.rlVersion
    apply<MavenPublishPlugin>()
}

subprojects {
    group = "com.openosrs.externals"

    project.extra["PluginProvider"] = "CJP10"
    project.extra["ProjectUrl"] = "https://github.com/CJP10/plugins"
    project.extra["PluginLicense"] = "MIT"

    repositories {
        jcenter {
            content {
                excludeGroupByRegex("com\\.openosrs.*")
            }
        }

        exclusiveContent {
            forRepository {
                mavenLocal()
            }
            filter {
                includeGroupByRegex("com\\.openosrs.*")
            }
        }

        exclusiveContent {
            forRepository {
                maven {
                    url = uri("https://repo.runelite.net")
                }
            }
            filter {
                includeModule("net.runelite", "discord")
            }
        }

        exclusiveContent {
            forRepository {
                maven {
                    url = uri("https://raw.githubusercontent.com/open-osrs/hosting/master")
                }
            }
            filter {
                includeModule("com.openosrs.rxrelay3", "rxrelay")
            }
        }
    }

    apply<JavaPlugin>()
    apply(plugin = "checkstyle")

    checkstyle {
        maxWarnings = 0
        toolVersion = "8.25"
        isShowViolations = true
        isIgnoreFailures = false
    }

    configure<PublishingExtension> {
        repositories {
            maven {
                url = uri("$buildDir/repo")
            }
        }
        publications {
            register("mavenJava", MavenPublication::class) {
                from(components["java"])
            }
        }
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        withType<Jar> {
            doLast {
                copy {
                    from("./build/libs/")
                    into("../release")
                }
            }
        }

        withType<AbstractArchiveTask> {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
            dirMode = 493
            fileMode = 420
        }
    }
}

tasks {

    task("cleanAll") {
        dependsOn(getTasksByName("clean", true))
    }

    task("buildAll") {
        dependsOn(getTasksByName("build", true))
    }

    task("jarAll") {
        dependsOn(getTasksByName("jar", true))
    }

    task("e2eBuild") {
        dependsOn("cleanAll")
        dependsOn("buildAll").mustRunAfter("cleanAll")
        dependsOn("jarAll").mustRunAfter("buildAll")
        dependsOn("bootstrapPlugins").mustRunAfter("jarAll")
    }
}
