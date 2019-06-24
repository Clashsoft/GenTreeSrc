# GenTreeSrc v0.1.0

+ Added code generation for regular and list properties. #1
+ Added code generation for `Impl` classes. #2
+ Added code generation for `of` methods. #3
+ Added code generation for `Visitor` classes. #4
+ Added code generation for `accept` methods. #5
+ Added support for `import` declarations. #6 #7 #8
+ Added support for alternative property syntax. #9
+ Added support for optional types. #10
+ Added the `-l` command-line option.

# GenTreeSrc v0.1.1

* No changes, this is a re-release.

# GenTreeSrc v0.2.0

* Renamed the `GenTreeSrc` class to `Main`.

# GenTreeSrc v0.3.0

* Updated command-line parsing with new `-o` option for output directory.

# GenTreeSrc v0.3.1

* The `--language` option value is now normalized to lowercase.

# GenTreeSrc v0.4.0

* The `Visitor.visit` method no longer uses the class name as a suffix. #12
* The `Visitor.visit` parameter now uses a lowercase first letter. #12
+ Added the `abstract` type modifier. #13
+ Added the `-d` / `--delete-old` option that enables deletion of old files in the output directory. #15

# GenTreeSrc v0.5.0

+ Added Map Types. #17
+ Added the `--visit-par`, `--no-visit-par`, `--visit-return` and `--visit-void` options. #16

# GenTreeSrc v0.6.0

+ Added support for named types with generic arguments. #20
+ Added the `import` type declaration modifier that prevents code generation. #19
* Import declarations are now treated like type declarations. #19

# GenTreeSrc v0.7.0

+ Added Array Types. #22
* Fixed `abstract` type declarations generating `Impl` classes. #21

# GenTreeSrc v0.8.0

+ Added the `readonly` property attribute. #23
+ Added the `noconstruct` property attribute. #24
+ Added the `delegate` property attribute. #25

# GenTreeSrc v0.9.0

+ Added the `--visit-default` option. #29
* Reduced the number of blank lines in generated code.
* The `accept` method is now implemented as a default method in the interface. #26
* The getter and setter of delegate properties are now implemented as default methods in the interface. #27
