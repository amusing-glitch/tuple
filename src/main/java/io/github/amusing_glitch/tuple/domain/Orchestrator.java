package io.github.amusing_glitch.tuple.domain;

import io.github.amusing_glitch.tuple.domain.codeGenerator.TupleFactoryGenerator;
import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;
import io.github.amusing_glitch.tuple.domain.javac.TupleScanner;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;
import io.github.amusing_glitch.tuple.domain.spec.TupleSpec;
import io.github.amusing_glitch.tuple.domain.validator.ValidationError;
import io.github.amusing_glitch.tuple.domain.codeGenerator.TupleClassGenerator;
import io.github.amusing_glitch.tuple.validators.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Orchestrator {
    private final List<TupleSpec<?>> tupleSpecs;
    private final TupleScanner tupleScanner;
    private final List<Validator> validators;
    private final TupleClassGenerator tupleClassGenerator;
    private final TupleFactoryGenerator tupleFactoryGenerator;

    public Orchestrator(
            List<TupleSpec<?>> tupleSpecs,
            TupleScanner tupleScanner,
            List<Validator> validators,
            TupleClassGenerator tupleClassGenerator,
            TupleFactoryGenerator tupleFactoryGenerator
    ) {
        this.tupleSpecs = tupleSpecs;
        this.tupleScanner = tupleScanner;
        this.validators = validators;
        this.tupleClassGenerator = tupleClassGenerator;
        this.tupleFactoryGenerator = tupleFactoryGenerator;
    }

    public void save() {
        List<TupleDefinition> tupleDefinitions = new ArrayList<>();
        tupleScanner.scan(signature ->
                getTupleSpec(signature)
                    .map(it -> it.process(signature))
                    .ifPresent(tupleDefinitions::add)
        );

        var validationResult = validate(tupleDefinitions);

        if (validationResult == null) {
            save(tupleDefinitions);
        }
    }

    private Optional<TupleSpec<?>> getTupleSpec(Signature signature) {
        return tupleSpecs.stream()
                .filter(tupleSpec -> tupleSpec.hasMatchingSignature(signature))
                .findFirst();
    }

    private TupleDefinition toDefinition(Signature signature) {
        return tupleSpecs.stream()
                .filter(tupleSpec -> tupleSpec.hasMatchingSignature(signature))
                .findFirst()
                .orElseThrow()
                .process(signature);
    }

    private List<ValidationError> validate(List<TupleDefinition> tupleDefinitions) {
        return null;
    }

    private void save(List<TupleDefinition> tupleDefinitions) {
        // write with tuple class generator and factory generator
    }
}
