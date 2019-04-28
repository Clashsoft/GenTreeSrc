# GenTreeSrc v0.1.0

+ Added code generation for regular and list properties. #1
+ Added code generation for `Impl` classes. #2
+ Added code generation for `of` methods. #3
+ Added code generation for `Visitor` classes. #4
+ Added code generation for `accept` methods. #5
+ Added support for `import` declarations. #8
+ Added support for alternative property syntax. #9
+ Added support for optional types. #10
+ Added the `-l` command-line option.

# GenTreeSrc v0.1.1

# GenTreeSrc v0.2.0

+ Added the `Parser` class.
* Moved the `ASTListener` class to the `antlr` package.
* Renamed the `GenTreeSrc` class to `Main`.
* Replaced references to the former `GenTreeSrc` class in `README.md` with `Main`.
- Made the `Main` class uninstantiable by making it `final` and making the default constructor `private`.
- Removed the `PropertyStyle` class.

# GenTreeSrc v0.3.0

+ Add a `javax.tools.Tool` implementation.
* Updated command-line parsing with new `-o` option for output directory.

# GenTreeSrc v0.3.1

* The `--language` option value is now normalized to lowercase.
* Renamed the `Java.stg` template to `java.stg`.

# GenTreeSrc v0.4.0

* The `Visitor.visit` method no longer uses the class name as a suffix. #12
* The `Visitor.visit` parameter now uses a lowercase first letter. #12
+ Added the `abstract` type modifier. #13
+ Added the `-d` / `--delete-old` option that enables deletion of old files in the output directory. #15
