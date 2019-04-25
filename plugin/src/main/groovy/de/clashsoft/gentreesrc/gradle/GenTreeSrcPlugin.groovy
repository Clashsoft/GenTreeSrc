package de.clashsoft.gentreesrc.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class GenTreeSrcPlugin implements Plugin<Project> {
	@Override
	void apply(Project project) {
		def taskName = 'gentreesrc'
		def inputDir = 'src/main/gentreesrc/'
		def outputDir = "$project.buildDir/generated-src/gentreesrc/main/"

		project.configurations.create(taskName)

		project.tasks.register(taskName, JavaExec) {
			classpath = project.configurations.gentreesrc
			main = 'de.clashsoft.gentreesrc.Main'
			args = [ '-o', gentreeSrcDir, inputDir ]

			inputs.dir(inputDir)
			outputs.dir(outputDir)
		}

		if (project.sourceSets) {
			project.sourceSets.main.java.srcDir(project.files(outputDir).builtBy(taskName))
		}
		if (project.tasks.compileJava) {
			project.tasks.compileJava.dependsOn(taskName)
		}
	}
}
