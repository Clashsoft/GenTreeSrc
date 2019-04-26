# GenTreeSrc

[![Build Status](https://travis-ci.org/Clashsoft/GenTreeSrc.svg?branch=master)](https://travis-ci.org/Clashsoft/GenTreeSrc)
[![Download](https://api.bintray.com/packages/clashsoft/maven/gentreesrc/images/download.svg)](https://bintray.com/clashsoft/maven/gentreesrc/_latestVersion)

A tool that generates Tree and Visitor classes from a description file.

## Installation

### Gradle

```groovy
repositories {
	// ...
	jcenter()
	// ...
}
// ...
dependencies {
	// ...
	testCompile group: 'de.clashsoft', name: 'gentreesrc', version: '0.3.1'
	// ...
}
```

It is recommended to use `testCompile` instead of `compile` because GenTreeSrc is only used at build time.

## Usage

1. Create a tree definition (`.gts`) file.
   It is recommended to place it in a test source directory like `src/test/gentreesrc/`.
   Refer to [Tree.gts](src/test/gentreesrc/Tree.gts) for an example.

2. Run the GenTreeSrc tool. There are two options:

   1. Using the IDE, by creating a run configuration with the following options:

      Option              | Value
      --------------------|--------------------------------------------
      Classpath of Module | test
      Main Class          | de.clashsoft.gentreesrc.Main
      Arguments           | src/test/gentreesrc/Tree.gts src/main/java/

   2. Using Gradle, by adding the following task definition to your `build.gradle` script:

      ```groovy
      task genTreeSrc(type: JavaExec) {
          classpath = sourceSets.test.compileClasspath
          main = 'de.clashsoft.gentreesrc.Main'
          args = [ 'src/main/gentreesrc/Tree.gts', 'src/main/java/' ]
      }
      ```

      You can execute the task every time you change your tree definition file, with one of the following commands:

      ```bash
      gradle genTreeSrc
      ./gradlew genTreeSrc
      gradlew genTreeSrc
      ```

3. The generated tree files can be found in the `src/main/java/` directory.

## Additional Notes

If you prefer not to pollute your `src/` directory with generated sources, you can use a different target directory.
This can be done by replacing `src/main/java/` in the examples above by a directory of your choice.

We recommend using the directory `build/generated-src/gentreesrc/main/`.
Add this line to your `build.gradle` script to mark it as a source directory
(otherwise you can't use the generated classes):

```groovy
sourceSets.main.java.srcDir "$buildDir/generated-src/gentreesrc/main/"
```
