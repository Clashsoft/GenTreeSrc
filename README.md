# GenTreeSrc

[![Java CI](https://github.com/Clashsoft/GenTreeSrc/actions/workflows/java-ci.yml/badge.svg)](https://github.com/Clashsoft/GenTreeSrc/actions/workflows/java-ci.yml)

A tool that generates Tree and Visitor classes from a description file.

## Installation

The easiest way to use GenTreeSrc is with the [Gradle plugin](https://github.com/Clashsoft/GenTreeSrc-Gradle), which is
available on the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/de.clashsoft.gentreesrc-gradle). Add the
following snippets to your `build.gradle` to set up the plugin:

```groovy
plugins {
	// ...
	// https://plugins.gradle.org/plugin/de.clashsoft.gentreesrc-gradle
	id 'de.clashsoft.gentreesrc-gradle' version '0.8.0'
	// ...
}
// ...
repositories {
	// ...
	mavenCentral()
	// ...
}
// ...
dependencies {
	// ...
	// https://mvnrepository.com/artifact/de.clashsoft/gentreesrc
	gentreesrc group: 'de.clashsoft', name: 'gentreesrc', version: '0.10.2'
	// ...
}
```

## Usage

1. Create a tree definition (`.gts`) file somewhere in the `src/main/gentreesrc` directory. Refer to the examples for
   guidance.

2. Run the `gentreesrcJava` Gradle task.

3. The generated classes will be placed in the `build/generated-src/gentreesrc/main/java/` directory. It is
   automatically configured as a source directory by the plugin, no extra setup is needed.

4. Repeat step 2 every time you change the tree definition file.

## Examples

Simple examples:

- [AbstractHierarchy.gts](src/test/gentreesrc/AbstractHierarchy.gts)
- [Properties.gts](src/test/gentreesrc/Properties.gts)
- [Types.gts](src/test/gentreesrc/Types.gts)

Advanced examples:

- [Tree.gts](src/main/gentreesrc/Tree.gts) - the definitions used by the GenTreeSrc tool itself
- [FulibScenarios.gts](https://github.com/fujaba/fulibScenarios/blob/master/src/main/gentreesrc/FulibScenarios.gts) - a
  full compiler using GenTreeSrc to generate its AST classes
