plugins {
    kotlin("plugin.serialization")
    kotlin("js")
    id("generate-karma-config")
    id("configure-ktlint")
}

val libs: (String) -> String by rootProject.ext
val libVersions: (String) -> String by rootProject.ext

kotlin {
    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    useConfigDirectory(project.buildDir.resolve("karmaConfig"))
                }
            }
        }
    }
}

dependencies {
    api(project(":shared"))

    api(kotlin("stdlib-js"))
    api(libs("kotlin-react"))
    api(libs("kotlin-react-dom"))
    api(npm("react", libVersions("react")))
    api(npm("react-dom", libVersions("react")))
    api(libs("kotlinx-coroutines-core-common"))
    api(libs("kotlinx-browser"))
    api(libs("kotlin-styled"))
    api(npm("styled-components", libVersions("styled-components")))

    api(npm("react-bootstrap", libVersions("react-bootstrap")))
    api(npm("@material-ui/core", libVersions("@material-ui/core")))
    api(npm("react-transition-group", libVersions("react-transition-group")))
    api(npm("bootstrap-switch-button-react", libVersions("bootstrap-switch-button-react")))

    api(npm("in-browser-download", libVersions("in-browser-download")))
    api(npm("get-image-pixels", libVersions("get-image-pixels")))
    api(npm("react-icons", libVersions("react-icons")))

    testImplementation(kotlin("test-js"))
}

