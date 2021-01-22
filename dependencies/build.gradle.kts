plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    jcenter()
}

gradlePlugin {
    plugins.register("dependencies-plugin") {
        id = "dependencies-plugin"
        implementationClass = "com.example.bliss.DependenciesPlugin"
    }
}