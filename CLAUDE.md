# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**io.github.amusing_glitch.tuple** is a Java library that dynamically generates strongly-typed tuples at compile time using an annotation processor. Instead of creating multiple small classes or using `Collection<Object>`, users can call `DynamicTuple.of(...)` or `DynamicTuple.named(...)` with arguments, and the annotation processor automatically generates type-safe `Tuple2`, `Tuple3`, etc. classes with proper generic type parameters.

## Development Commands

### Building
```bash
./mvnw clean compile
```

### Testing
Run all tests:
```bash
./mvnw test
```

Run a single test class:
```bash
./mvnw test -Dtest=TupleSpecProcessorIntegrationTest
```

Run a specific test method:
```bash
./mvnw test -Dtest=TupleSpecProcessorIntegrationTest#shouldCreateTheTupleClassesAndFactoryMethodForDefinitionInClass
```

### Packaging & Publishing
```bash
./mvnw clean install              # Build and install locally
./mvnw clean deploy               # Deploy to Maven Central (requires credentials)
```

## Architecture Overview

### High-Level Flow
1. **User Code**: Calls facade methods like `DynamicTuple.of("Alice", 28)` or `DynamicTuple.named(Student.type, ...)`
2. **Annotation Processor** (`TupleSpecProcessor`): Triggered during compilation
3. **Scanner** (`TupleDefinitionScanner`): Parses the AST to find all tuple usage patterns
4. **Code Generation**: Uses Pebble templates to generate Tuple classes and factory method overloads
5. **Validators**: Ensures named tuple arguments are consistent across the codebase

### Key Components

#### DynamicTupleSeed (src/main/java/.../dynamic/DynamicTupleSeed.java)
- Facade class with four overloaded methods that act as entry points:
  - `of(Object... args)` - numbered tuple factory (variadic fallback)
  - `of(T type, FieldSpec<?>... fieldSpecs)` - named tuple factory
  - `zip(Stream<?>... streams)` - numbered tuple zip for streams
  - `namedZip(T type, StreamFieldSpec<?>... streamFieldSpecs)` - named tuple zip
- Methods throw `RuntimeException` because they're replaced by generated overloads during compilation
- The `zip(List<Stream<Object>> streams)` helper delegates to `Zipper.zip()`

#### TupleSpecProcessor (src/main/java/.../processors/TupleSpecProcessor.java)
- Extends `OncePerLifecycleProcessor` (ensures it runs once per compile lifecycle)
- Scans for four tuple definition patterns:
  - `TUPLE_FACTORY_METHOD_SPEC` - `DynamicTuple.of(...)` calls
  - `TUPLE_ZIP_METHOD_SPEC` - `DynamicTuple.zip(...)` calls
  - `NAMED_TUPLE_FACTORY_METHOD_SPEC` - `DynamicTuple.named(...)` calls
  - `NAMED_TUPLE_ZIP_METHOD_SPEC` - `DynamicTuple.namedZip(...)` calls
- Uses a `TaskListener` to hook into the compilation pipeline and intercept after PARSE phase
- Delegates to `DynamicTupleGenerator` to create generation parameters
- Writes generated files via `JavaFileWriter`

#### TupleDefinitionScanner (src/main/java/.../javac/TupleDefinitionScanner.java)
- Walks the compilation AST to find tuple invocations
- Returns a `TupleDefinitionScanResult` containing:
  - `NumberedTupleDefinition` - arity and type arguments for `DynamicTuple.of(...)` calls
  - `NamedTupleDefinition` - class name and field specs for `DynamicTuple.named(...)` calls
- Critical: Handles both regular factories and stream zipping methods

#### Code Generation
- **Pebble Templates** (src/main/resources/templates/):
  - `DynamicTuple.peb` - main facade class with all overloaded methods
  - `Tuple.peb` - individual Tuple record template
  - `NamedTuple.peb` - named tuple record template
  - `StaticTupleFactory.peb` - factory method overload
  - `StaticNamedTupleFactory.peb` - named factory method overload
  - `ZipperMethod.peb` - stream zipping method for numbered tuples
  - `NamedZipperMethod.peb` - stream zipping method for named tuples
- **GeneratedClassSchema** - encapsulates the data needed to render templates
- **JavaFileWriter** - uses Filer to create source files on disk

#### Validators (src/main/java/.../validators/)
- `NamedTupleArgumentOrderValidator` - ensures that if a named tuple class (e.g., `Student`) is used in multiple places, all usages have the same field order and types
- Runs in the final compilation phase to catch inconsistencies early
- Thrown errors become compilation errors

### Special Compilation Concerns
- **JDK Internals Access**: The test suite (pom.xml `maven-surefire-plugin`) requires `--add-opens` flags to access internal javac APIs:
  - `jdk.compiler/com.sun.tools.javac.api`
  - `jdk.compiler/com.sun.tools.javac.processing`
  - `jdk.compiler/com.sun.tools.javac.tree` (for AST walking)
  - And others in `com.sun.tools.javac.*`

## Development Guidelines

### Adding New Tuple Features
1. **Modify DynamicTupleSeed**: Add new facade method signature
2. **Update TupleDefinitionSpec**: Add pattern to `SupportedTupleDefinitions` if it's a new calling pattern
3. **Update Scanner**: Extend `TupleDefinitionScanner` to recognize the new pattern in the AST
4. **Create Template**: Add `.peb` template if generating a new type of code
5. **Add Tests**: Use `compile-testing` to verify the new feature works end-to-end

### Testing Integration
- Tests use Google's `compile-testing` library to compile source snippets with the processor
- `JavaFileObjects.forSourceLines(...)` creates in-memory source files
- `javac().withProcessors(...).compile(...)` runs the annotation processor
- `assertThat(compilation).generatedSourceFile(...)` verifies generated code

### Tuple Generation Patterns to Know
- **Deduplication**: Multiple calls to `DynamicTuple.of(String, Integer)` generate ONE `Tuple2<String, Integer>` class and ONE factory overload
- **Named Tuples**: The field name is determined by the lambda variable name in `FieldSpec<T>` (e.g., `name -> "value"`)
- **Generics Only**: Named tuple fields use generics (`T0`, `T1`, etc.) because actual type information isn't available to the annotation processor without breaking the compiler state
- **Equality Override**: Generated records include an explicit `equals()` override (not the default record implementation)

## Common Debugging

### Compilation Fails with "Facade method: Operation not permitted"
This happens if code calls `DynamicTuple.of(...)` but the annotation processor doesn't run or doesn't generate the overload. Ensure:
- Annotation processor is on the classpath
- No `<proc>none</proc>` configuration in build that disables processors for this module
- Recompile from scratch: `./mvnw clean compile`

### Generated Classes Have Wrong Generics
Check `NamedTupleArgumentOrderValidator` — inconsistent field order/types across usages is flagged here. Review all calls to the same named tuple class.

### Adding `@SupportedAnnotationTypes("*")`
The processor watches all annotations (the `"*"` pattern) to catch tuple invocations anywhere in the code. This is intentional and necessary.

## Dependencies

- **Pebble 4.1.0** - Template engine for code generation
- **JUnit 5.9.1** - Test framework
- **Mockito 5.21.0** - Test mocking
- **Google compile-testing 0.23.0** - Testing annotation processors
- **Java 21** - Target and source version
