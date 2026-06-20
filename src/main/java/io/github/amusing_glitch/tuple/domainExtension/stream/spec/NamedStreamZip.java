package io.github.amusing_glitch.tuple.domainExtension.stream.spec;

import io.github.amusing_glitch.tuple.domain.codeGenerator.TupleExtensionGenerator;
import io.github.amusing_glitch.tuple.domain.definition.NamedFieldDefinition;
import io.github.amusing_glitch.tuple.domain.definition.NamedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;
import io.github.amusing_glitch.tuple.domain.javac.signature.MethodNomenclature;
import io.github.amusing_glitch.tuple.domain.javac.signature.Type;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.LambdaArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.TypeRefArgument;
import io.github.amusing_glitch.tuple.domain.spec.TupleExtensionSpec;
import io.github.amusing_glitch.tuple.domainExtension.stream.codeGenerator.NamedStreamExtensionGenerator;
import io.github.amusing_glitch.tuple.dynamic.templates.JavaTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class NamedStreamZip extends TupleExtensionSpec<NamedTupleDefinition> {
    private static final MethodNomenclature targetMethodNomenclature = new MethodNomenclature(
            JavaTemplate.packageName,
            JavaTemplate.dynamicTupleClassName,
            JavaTemplate.dynamicNamedTupleZipMethodName
    );
    private final NamedStreamExtensionGenerator extensionGenerator;

    public NamedStreamZip(NamedStreamExtensionGenerator extensionGenerator) {
        this.extensionGenerator = extensionGenerator;
    }

    @Override
    protected MethodNomenclature targetMethodNomenclature() {
        return targetMethodNomenclature;
    }

    @Override
    protected boolean hasMatchingArguments(List<Argument> arguments) {
        return Stream.of(
                arguments.stream().findFirst().orElseThrow() instanceof TypeRefArgument,
                arguments.stream().skip(1).allMatch(it -> it instanceof LambdaArgument)
        ).reduce(Boolean::logicalAnd).orElseThrow();
    }

    @Override
    public NamedTupleDefinition process(Signature signature) {
        String tupleClassName = ((TypeRefArgument) signature.arguments().stream().findFirst().orElseThrow()).name();
        var fields = signature.arguments().stream()
                .skip(1)
                .map(it -> (LambdaArgument) it)
                .map(it ->
                        new NamedFieldDefinition(
                                it.name(),
                                it.type()
                                        .map(Type::innerType)
                                        .map(Optional::orElseThrow)
                                        .map(Type::value)
                        )
                ).toList();

        return new NamedTupleDefinition(
                JavaTemplate.packageName,
                tupleClassName,
                fields,
                signature.node()
        );
    }

    @Override
    protected TupleExtensionGenerator<NamedTupleDefinition> tupleExtensionGenerator() {
        return extensionGenerator;
    }
}
