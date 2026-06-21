package io.github.amusing_glitch.tuple.domainExtension.stream.spec;

import io.github.amusing_glitch.tuple.domain.codeGenerator.TupleExtensionGenerator;
import io.github.amusing_glitch.tuple.domain.definition.NumberedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.javac.signature.MethodNomenclature;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.SimpleArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed.TypedSimpleArgument;
import io.github.amusing_glitch.tuple.domain.spec.TupleExtensionSpec;
import io.github.amusing_glitch.tuple.domainExtension.stream.codeGenerator.NumberedStreamExtensionGenerator;
import io.github.amusing_glitch.tuple.dynamic.templates.JavaTemplate;

import java.util.List;

public class NumberedStreamZipSpec extends TupleExtensionSpec<NumberedTupleDefinition> {
    private final NumberedStreamExtensionGenerator extensionGenerator;

    private static final MethodNomenclature targetMethodNomenclature = new MethodNomenclature(
            JavaTemplate.packageName,
            JavaTemplate.dynamicTupleClassName,
            JavaTemplate.dynamicTupleZipMethodName
    );

    public NumberedStreamZipSpec(NumberedStreamExtensionGenerator extensionGenerator) {
        this.extensionGenerator = extensionGenerator;
    }

    @Override
    protected MethodNomenclature targetMethodNomenclature() {
        return targetMethodNomenclature;
    }

    @Override
    protected boolean hasMatchingArguments(List<? extends Argument> arguments) {
        return arguments.stream().allMatch(it -> it instanceof SimpleArgument) &&
                arguments.stream()
                        .map(it -> (SimpleArgument) it)
                        .allMatch(it -> {
                            if (it instanceof TypedSimpleArgument typed) {
                                return typed.type().value().equals("Stream");
                            }
                            return true;
                        });
    }

    @Override
    public NumberedTupleDefinition process(Signature signature) {
        return new NumberedTupleDefinition(
                JavaTemplate.packageName,
                JavaTemplate.className(signature.arguments().size()),
                signature.arguments().size(),
                signature.node()
        );
    }

    @Override
    protected TupleExtensionGenerator<NumberedTupleDefinition> tupleExtensionGenerator() {
        return extensionGenerator;
    }
}
