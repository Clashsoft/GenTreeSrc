# GenTreeSrc

[![Build Status](https://travis-ci.org/Clashsoft/GenTreeSrc.svg?branch=master)](https://travis-ci.org/Clashsoft/GenTreeSrc)
[![Download](https://api.bintray.com/packages/clashsoft/maven/gentreesrc/images/download.svg)](https://bintray.com/clashsoft/maven/gentreesrc/_latestVersion)

A tool that generates Tree and Visitor classes from a description file.

## Installation

The easiest way to use GenTreeSrc is with the [Gradle plugin](https://github.com/Clashsoft/GenTreeSrc-Gradle),
which is available on the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/de.clashsoft.gentreesrc-gradle).
Add the following snippets to your `build.gradle` to set up the plugin:

```groovy
plugins {
	// ...
	// https://plugins.gradle.org/plugin/de.clashsoft.gentreesrc-gradle
	id 'de.clashsoft.gentreesrc-gradle' version '0.2.0'
	// ...
}
// ...
repositories {
	// ...
	jcenter()
	// ...
}
// ...
dependencies {
	// ...
	// https://mvnrepository.com/artifact/de.clashsoft/gentreesrc
	gentreesrc group: 'de.clashsoft', name: 'gentreesrc', version: '0.3.1'
	// ...
}
```

## Usage

1. Create a tree definition (`.gts`) file somewhere in the `src/main/gentreesrc` directory.
   Refer to [Tree.gts](src/test/gentreesrc/Tree.gts) for an example.
   
2. Run the `gentreesrcJava` Gradle task.
   
3. The generated classes will be placed in the `build/generated-src/gentreesrc/main/java/` directory.
   It is automatically configured as a source directory by the plugin, no extra setup is needed.

4. Repeat step 2 every time you change the tree definition file.
