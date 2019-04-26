package de.clashsoft.gentreesrc.gradle

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class FunctionalTest extends Specification {
	@Rule
	TemporaryFolder testProjectDir = new TemporaryFolder()

	def setup() {
		testProjectDir.newFile('settings.gradle') << """
		rootProject.name = 'test'
		"""

		testProjectDir.newFile('build.gradle') << """
		plugins {
			id 'java'
			id 'de.clashsoft.gentreesrc-gradle'
		}
		
		repositories {
			jcenter()
		}
		
		dependencies {
			gentreesrc group: 'de.clashsoft', name: 'gentreesrc', version: '+'
		}
		"""

		testProjectDir.newFolder('src', 'main', 'gentreesrc')
		testProjectDir.newFile('src/main/gentreesrc/Test.gts') << """
		com.example.Foo {
			Bar(text: String)
			Baz(value: int)
		}
		"""
	}

	def run() {
		when:
		def result = GradleRunner.create()
				.withProjectDir(testProjectDir.root)
				.withArguments('gentreesrcJava')
				.withPluginClasspath()
				.build()

		println "-" * 30 + " Gradle Output " + "-" * 30
		println result.output
		println "-" * 30 + " Project Files " + "-" * 30
		testProjectDir.root.eachFileRecurse {
			println it
		}
		println "-" * 75

		then:
		result.task(":gentreesrcJava").outcome == SUCCESS

		def outputDir = new File(testProjectDir.root, "build/generated-src/gentreesrc/main/java/")
		new File(outputDir, 'com/example/Foo.java').exists()
		new File(outputDir, 'com/example/Bar.java').exists()
		new File(outputDir, 'com/example/Baz.java').exists()
	}
}
