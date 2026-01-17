package com.aparigraha.tuple;

import com.aparigraha.tuple.generator.TupleGenerationParams;
import com.aparigraha.tuple.generator.TupleGenerator;
import com.aparigraha.tuple.generator.TupleSchema;
import com.aparigraha.tuple.generator.TupleSchemaWriter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Stream;


@SupportedAnnotationTypes({"com.aparigraha.tuple.TupleSpec", "com.aparigraha.tuple.NamedTupleSpec"})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class TupleSpecProcessor extends AbstractProcessor {
    private static final String packageName = "com.aparigraha.tuples";
    private static final String tuple = "Tuple";
    private static final String fieldPrefix = "item";
    private static final Set<Class<? extends Annotation>> targetAnnotations = Set.of(
            TupleSpec.class,
            NamedTupleSpec.class
    );

    private final TupleGenerator tupleGenerator;
    private final TupleSchemaWriter tupleSchemaWriter;

    public TupleSpecProcessor(TupleGenerator tupleGenerator, TupleSchemaWriter tupleSchemaWriter) {
        super();
        this.tupleGenerator = tupleGenerator;
        this.tupleSchemaWriter = tupleSchemaWriter;
    }

    public TupleSpecProcessor() {
        this(new TupleGenerator(), new TupleSchemaWriter());
    }

    private Stream<TupleSpec> tupleSpecs(
            Set<? extends TypeElement> annotations,
            List<? extends Element> elements
    ) {
        if (annotations.stream().map(a -> a.getQualifiedName().toString()).anyMatch(TupleSpec.class.getCanonicalName()::equals)) {
            return elements.stream()
                    .map(element -> element.getAnnotation(TupleSpec.class));
        } else {
            return Stream.of();
        }
    }

    private Stream<NamedTupleSpec> namedTupleSpecs(
            Set<? extends TypeElement> annotations,
            List<? extends Element> elements
    ) {
        if (annotations.stream().map(a -> a.getQualifiedName().toString()).anyMatch(NamedTupleSpec.class.getCanonicalName()::contentEquals)) {
            return elements.stream()
                    .map(element -> element.getAnnotation(NamedTupleSpec.class));
        } else {
            return Stream.of();
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var elements = annotations.stream()
                .map(roundEnv::getElementsAnnotatedWith)
                .flatMap(Set::stream)
                .distinct()
                .toList();

        var tupleSpecParam = tupleSpecs(annotations, elements)
                .filter(Objects::nonNull)
                .flatMap(tupleSpec -> Arrays.stream(tupleSpec.value()).boxed())
                .map(size -> new TupleGenerationParams(
                        packageName,
                        tuple + size,
                        fieldPrefix,
                        size
                ));

        var namedTupleSpecParam = namedTupleSpecs(annotations, elements)
                .filter(Objects::nonNull)
                .flatMap(namedTupleSpec -> Arrays.stream(namedTupleSpec.value()))
                .map(classSpec -> new TupleGenerationParams(
                        packageName,
                        classSpec.className(),
                        Arrays.stream(classSpec.fields()).toList()
                ));

        Stream.concat(tupleSpecParam, namedTupleSpecParam)
                .distinct()
                .map(this::generateTuple)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(this::saveTupleSchema);

        return canClaim(annotations);
    }


    private boolean canClaim(Set<? extends TypeElement> annotations) {
        var supportedAnnotations = getSupportedAnnotationTypes();
        return annotations.stream()
                .map(a -> a.getQualifiedName().toString())
                .anyMatch(supportedAnnotations::contains);
    }


    private Optional<TupleSchema> generateTuple(TupleGenerationParams params) {
        try {
            return Optional.of(tupleGenerator.generate(params));
        } catch (IOException exception) {
            if (processingEnv != null)
                processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "Failed creating tuple: \"%s.%s\". Exception: %s".formatted(
                                params.packageName(),
                                params.className(),
                                exception.getMessage()
                        )
                );
            return Optional.empty();
        }
    }


    private void saveTupleSchema(TupleSchema tupleSchema) {
        try {
            tupleSchemaWriter.write(tupleSchema.javaCode(), tupleSchema.completeClassName(), processingEnv);
        } catch (IOException exception) {
            if (processingEnv != null)
                processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "Failed creating tuple: \"%s.%s\". Exception: %s".formatted(
                                tupleSchema.packageName(),
                                tupleSchema.className(),
                                exception.getMessage()
                        )
                );
        }
    }
}
