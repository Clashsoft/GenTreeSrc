package de.clashsoft.gentreesrc.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.JavaExec

class GenTreeSrcPlugin implements Plugin<Project> {
	@Override
	void apply(Project project) {
		String language = 'java'
		String sourceSet = 'main'

		String configurationName = 'gentreesrc' + (sourceSet == 'main' ? '' : sourceSet.capitalize())
		String taskName = configurationName + language.capitalize()
		String inputDir = "src/$sourceSet/gentreesrc/"
		String outputDir = "$project.buildDir/generated-src/gentreesrc/$sourceSet/$language"

		Configuration configuration = project.configurations.create(configurationName)

		project.tasks.register(taskName, JavaExec) {
			classpath = configuration
			main = 'de.clashsoft.gentreesrc.Main'
			args = [ '-o', outputDir, inputDir ]

			inputs.dir(inputDir)
			outputs.dir(outputDir)
		}

		JavaPluginConvention javaPluginConvention = project.convention.findPlugin(JavaPluginConvention)
		if (javaPluginConvention != null) {
			def sourceSets = javaPluginConvention.sourceSets

			sourceSets.main.java.srcDir(project.files(outputDir).builtBy(taskName))
		}

		Task compileJava = project.tasks.findByName('compileJava')
		if (compileJava != null) {
			compileJava.dependsOn(taskName)
		}
	}
}
