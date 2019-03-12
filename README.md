# GenTreeSrc

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
	testCompile group: 'de.clashsoft', name: 'gentreesrc', version: '0.1.0'
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
      Main Class          | de.clashsoft.gentreesrc.GenTreeSrc
      Arguments           | src/test/gentreesrc/Tree.gts src/main/java/

   2. Using Gradle, by adding the following task definition to your `build.gradle` script:

      ```groovy
      task genTreeSrc(type: JavaExec) {
          classpath = sourceSets.test.compileClasspath
          main = 'de.clashsoft.gentreesrc.GenTreeSrc'
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
