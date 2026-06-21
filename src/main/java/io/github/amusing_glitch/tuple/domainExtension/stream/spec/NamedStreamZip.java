package io.github.amusing_glitch.tuple.domainExtension.stream.spec;

import io.github.amusing_glitch.tuple.domain.codeGenerator.TupleExtensionGenerator;
import io.github.amusing_glitch.tuple.domain.definition.BasicNamedFieldDefinition;
import io.github.amusing_glitch.tuple.domain.definition.NamedFieldDefinition;
import io.github.amusing_glitch.tuple.domain.definition.NamedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.definition.TypedNamedFieldDefinition;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;
import io.github.amusing_glitch.tuple.domain.javac.signature.MethodNomenclature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.basic.BasicLambdaArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.LambdaArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed.TypedLambdaArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.TypeRefArgument;
import io.github.amusing_glitch.tuple.domain.spec.TupleExtensionSpec;
import io.github.amusing_glitch.tuple.domainExtension.stream.codeGenerator.NamedStreamExtensionGenerator;
import io.github.amusing_glitch.tuple.dynamic.templates.JavaTemplate;

import java.util.List;

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
    protected boolean hasMatchingArguments(List<? extends Argument> arguments) {
        return arguments.stream().findFirst().orElseThrow() instanceof TypeRefArgument &&
                arguments.stream().skip(1).allMatch(it -> it instanceof LambdaArgument || it instanceof TypedLambdaArgument);
    }

    @Override
    public NamedTupleDefinition process(Signature signature) {
        String tupleClassName = ((TypeRefArgument) signature.arguments().stream().findFirst().orElseThrow()).name();
        var fields = signature.arguments().stream()
                .skip(1)
                .map(it -> (LambdaArgument) it)
                .map(it -> {
                    if (it instanceof TypedLambdaArgument typed) {
                        return (NamedFieldDefinition) new TypedNamedFieldDefinition(
                                typed.name(),
                                typed.type(),
                                typed.node()
                        );
                    } else if (it instanceof BasicLambdaArgument basic) {
                        return (NamedFieldDefinition) new BasicNamedFieldDefinition(
                                basic.name(),
                                basic.node()
                        );
                    }
                    throw new AssertionError("Exhaustive LambdaArgument match");
                })
                .toList();

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
