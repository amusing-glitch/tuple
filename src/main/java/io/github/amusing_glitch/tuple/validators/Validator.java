package io.github.amusing_glitch.tuple.validators;

import io.github.amusing_glitch.tuple.javac.TupleDefinitionScanResult;


public interface Validator {
    void validate(TupleDefinitionScanResult tupleDefinitionScanResult) throws Exception;
}
