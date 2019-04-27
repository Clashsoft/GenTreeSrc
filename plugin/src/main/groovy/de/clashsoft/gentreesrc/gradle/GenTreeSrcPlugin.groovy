package de.clashsoft.gentreesrc.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.plugins.JavaPlugin
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

		project.plugins.withType(JavaPlugin) {
			// configure source directory
			project.sourceSets.main.java.srcDir(project.files(outputDir).builtBy(taskName))

			// configure compile dependency
			project.tasks.compileJava.dependsOn(taskName)
		}
	}
}
