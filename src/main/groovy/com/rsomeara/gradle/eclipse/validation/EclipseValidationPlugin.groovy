package com.rsomeara.gradle.eclipse.validation

import org.gradle.api.Plugin
import org.gradle.api.Project

public class EclipseValidationPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.task('hello') << { println "Hello from the EclipseValidationPlugin" }
    }
}