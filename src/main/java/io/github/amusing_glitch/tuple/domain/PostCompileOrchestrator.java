package io.github.amusing_glitch.tuple.domain;

import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;
import io.github.amusing_glitch.tuple.domain.javac.TupleScanner;
import io.github.amusing_glitch.tuple.domain.type.TypeInfo;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;
import io.github.amusing_glitch.tuple.domain.spec.TypedTupleSpec;
import io.github.amusing_glitch.tuple.domain.validator.TypedTupleValidator;
import io.github.amusing_glitch.tuple.domain.validator.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostCompileOrchestrator {
    private final List<TypedTupleSpec<?>> typedTupleSpecs;
    private final TupleScanner tupleScanner;
    private final List<TypedTupleValidator<?>> typedValidators;

    public PostCompileOrchestrator(
            List<TypedTupleSpec<?>> typedTupleSpecs,
            TupleScanner tupleScanner,
            List<TypedTupleValidator<?>> typedValidators
    ) {
        this.typedTupleSpecs = typedTupleSpecs;
        this.tupleScanner = tupleScanner;
        this.typedValidators = typedValidators;
    }

    public List<ValidationError> validate() {
        List<TupleDefinition> typedDefinitions = new ArrayList<>();
        tupleScanner.scan(signature ->
                getTupleSpec(signature)
                    .map(it -> it.process(signature))
                    .ifPresent(typedDefinitions::add)
        );

        return validateDefinitions(typedDefinitions);
    }

    @SuppressWarnings("unchecked")
    private <T extends TupleDefinition & TypeInfo> List<ValidationError> validateDefinitions(
            List<TupleDefinition> definitions
    ) {
        List<ValidationError> allErrors = new ArrayList<>();

        for (TypedTupleValidator<?> validator : typedValidators) {
            TypedTupleValidator<T> typedValidator = (TypedTupleValidator<T>) validator;
            List<T> typedDefs = (List<T>) (Object) definitions;
            allErrors.addAll(typedValidator.validate(typedDefs));
        }

        return allErrors;
    }

    private Optional<TypedTupleSpec<?>> getTupleSpec(Signature signature) {
        return typedTupleSpecs.stream()
                .filter(spec -> spec.hasMatchingSignature(signature))
                .findFirst();
    }
}
