# Divine Intervention
Adds some features for use within mixins

## Injectors
### ModifyOperand
Takes the topmost value on the operand stack and passes it to a handler method,
optionally also capturing the target method arguments, and replaces it with the
handler method's return value.

### EnumInject
A slightly weird injector that adds a constant to an enum. See [EnumInject.md](EnumInject.md) for an example.

### CustomInject
A quick way of using asm to do whatever you want to do. If you don't know how to use it, you probably shouldn't.

## Installation
### Groovy
build.gradle:
```groovy
repositories {
    maven {
        name = "Jitpack"
        url = "https://jitpack.io"
    }
}

dependencies {
    modImplementation annotationProcessor(include("com.github.devs-immortal:Divine-Intervention:${project.divine_intervention_version}"))
}
```
gradle.properties:
```properties
divine_intervention_version = 2.0.0
```

### Kotlin
build.gradle.kts:
```kotlin
val divineInterventionVersion: String by properties

repositories {
    maven {
        name = "Jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    modImplementation(
            group = "net.immortaldevs",
            name = "Divine-Intervention",
            version = divineInterventionVersion,
    ).also(::annotationProcessor).also(::include)
}
```
gradle.properties:
```properties
divineInterventionVersion = 2.0.0
```
