package com.aparigraha.tuple.generator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;


public class TupleSchemaWriter {
    public void write(
            String tupleSchema,
            String path,
            ProcessingEnvironment processingEnv
    ) throws IOException {
        JavaFileObject file = processingEnv
                .getFiler()
                .createSourceFile(path);
        try (PrintWriter out = new PrintWriter(file.openWriter())) {
            out.print(tupleSchema);
        }
    }
}
