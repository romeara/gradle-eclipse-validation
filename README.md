# Gradle Eclipse Validation

Gradle plug-in which allows configuration of [Eclipse validation preferences](doc/eclipse_validation.md)

# Use 

<aside class="notice">
NOTE: This plug-in is not currently publicly released, and is only deployed for testing. You must run the `install` command to deploy the plug-in to your local repository before use
</aside>

To use in test projects, apply the plug-in via:

    buildscript {
        repositories {
            mavenLocal()
        }
        configurations.all {
            // check for updates every build
            resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
        }
        dependencies {
            classpath group: 'com.rsomeara', name: 'gradle-eclipse-validation', version: '0.1-SNAPSHOT', changing: true
        }
    }

    apply plugin: 'com.rsomeara.gradle.eclipse.validation'
    
# Environment/Languages

The plug-in uses Java 1.6 to match the Gradle 2.0 minimum environment

# Licensing

This plug-in is licensed in the same way as Gradle, under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0). See the [notices file](doc/notices.md) for copyright information