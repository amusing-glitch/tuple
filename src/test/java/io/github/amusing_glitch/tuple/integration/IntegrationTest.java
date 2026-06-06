package io.github.amusing_glitch.tuple.integration;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import io.github.amusing_glitch.tuple.processors.TupleSpecProcessor;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class IntegrationTest {
    @Test
    void shouldSupportBothNamedAndNamedZipOnSameType() {
        JavaFileObject dependant = JavaFileObjects.forSourceLines(
                "com.example.Main",
                "package com.example;",
                "import static io.github.amusing_glitch.tuple.dynamic.DynamicTuple.*;",
                "import java.util.stream.Stream;",
                "public class Main {",
                "   public static void main(String[] args) {",
                "       var t = named(Student.type, name -> \"Ashwin\", age -> 28);",
                "       var t1 = namedZip(Student.type, name -> Stream.of(\"Ashwin\"), age -> Stream.of(28));",
                "   }",
                "}"
        );

        Compilation compilation = javac()
                .withProcessors(new TupleSpecProcessor())
                .compile(dependant);

        assertThat(compilation).succeeded();
        assertThat(compilation)
                .generatedSourceFile("com.example.Student")
                .hasSourceEquivalentTo(JavaFileObjects.forSourceLines(
                        "com.example.Student",
                        "package com.example;\n" +
                                "\n" +
                                "\n" +
                                "public record Student<T0, T1> (T0 name, T1 age) {\n" +
                                "    public static final Student type = null;" +
                                "    @Override\n" +
                                "    public boolean equals(Object obj) {\n" +
                                "        if (obj instanceof Student<?, ?> that) {\n" +
                                "            return this.name == that.name && this.age == that.age;\n" +
                                "        } else return false;\n" +
                                "    }\n" +
                                "}"
                ));
    }
}
