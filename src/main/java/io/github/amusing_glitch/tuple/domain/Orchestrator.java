package io.github.amusing_glitch.tuple.domain;

import io.github.amusing_glitch.tuple.domain.codeGenerator.TupleClassGenerator;
import io.github.amusing_glitch.tuple.domain.codeGenerator.TupleFactoryGenerator;
import io.github.amusing_glitch.tuple.domain.javac.TupleScanner;
import io.github.amusing_glitch.tuple.domain.spec.TupleSpec;
import io.github.amusing_glitch.tuple.validators.Validator;

import java.util.List;

public class Orchestrator extends PreCompileOrchestrator {
    public Orchestrator(
            List<TupleSpec<?>> tupleSpecs,
            TupleScanner tupleScanner,
            List<Validator> validators,
            TupleClassGenerator tupleClassGenerator,
            TupleFactoryGenerator tupleFactoryGenerator
    ) {
        super(tupleSpecs, tupleScanner, validators, tupleClassGenerator, tupleFactoryGenerator);
    }
}
