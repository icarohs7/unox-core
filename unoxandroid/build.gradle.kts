plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    id("com.github.dcendents.android-maven") version "2.1"
    id("jacoco")
    defaults.`android-module`
}

android {
    defaultSettings()
    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
    }
}

dependencies {
    api(Deps.arrowCore)
    api(Deps.arrowEffects)
    api(Deps.jsonIter)
    api(Deps.kotlinStdLib)
    api(Deps.rxKotlin)
    api(Deps.rxRelay)

    api(AndroidDeps.appCompat)
    api(AndroidDeps.coreKtx)
    api(AndroidDeps.coroutinesAndroid)
    api(AndroidDeps.disposer)
    api(AndroidDeps.lifecycleExtensions)
    api(AndroidDeps.lives)
    api(AndroidDeps.rxAndroid)
    api(AndroidDeps.timber)
}

jacoco {
    toolVersion = "0.8.3"
}

tasks {
    withType<Test> {
        extensions.getByType<JacocoTaskExtension>().setIncludeNoLocationClasses(true)
    }

    create<JacocoReport>("jacocoTestReport") {
        dependsOn("testDebugUnitTest", "createDebugCoverageReport")

        this.group = "Reporting"
        this.description = "Generate Jacoco coverage reports for Debug build"

        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }

        val excludes = listOf(
                "**/R.class",
                "**/R\$*.class",
                "**/*\$ViewInjector*.*",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "**/*Test*.*",
                "android/**/*.*"
        )

        classDirectories.setFrom(
                fileTree("$buildDir/intermediates/classes/debug") { exclude(excludes) },
                fileTree("$buildDir/tmp/kotlin-classes/debug") { exclude(excludes) }
        )
        sourceDirectories.setFrom(files(
                android.sourceSets["main"].java.srcDirs,
                "src/main/kotlin"
        ))
        executionData("$buildDir/jacoco/testDebugUnitTest.exec")
    }
}