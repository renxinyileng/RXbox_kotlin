pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
        maven { url =uri("https://maven.aliyun.com/repository/releases") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url =uri("https://maven.aliyun.com/repository/releases") }
    }
}

rootProject.name = "RX盒子"
include(":app")
