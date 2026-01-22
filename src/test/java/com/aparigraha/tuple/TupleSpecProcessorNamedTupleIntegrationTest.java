package com.aparigraha.tuple;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;


class TupleSpecProcessorNamedTupleIntegrationTest {
    @Test
    void shouldGenerateClassesForNamedTupleSpec() {
        JavaFileObject dependant = JavaFileObjects.forSourceLines(
                "com.example.Main",
                "package com.example;",
                "import static com.aparigraha.tuple.dynamic.DynamicTuple.named;",
                "import java.util.stream.Stream;",
                "public class Main {",
                "   public static void main(String[] args) {",
                "       named(Student.class, name -> \"Alice\");",
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
                                "public record Student<T0> (T0 name) {\n" +
                                "    @Override\n" +
                                "    public boolean equals(Object obj) {\n" +
                                "        if (obj instanceof Student<?> that) {\n" +
                                "            return this.name == that.name;\n" +
                                "        } else return false;\n" +
                                "    }\n" +
                                "}"
                ));
    }
}