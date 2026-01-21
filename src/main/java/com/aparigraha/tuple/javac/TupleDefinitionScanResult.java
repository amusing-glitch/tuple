package com.aparigraha.tuple.javac;

import java.util.HashSet;
import java.util.Set;


public record TupleDefinitionScanResult (
        Set<TupleDefinition> tupleDefinitions,
        Set<NamedTupleDefinition> namedTupleDefinitions
) {
    public TupleDefinitionScanResult() {
        this(new HashSet<>(), new HashSet<>());
    }

    public TupleDefinitionScanResult add(TupleDefinitionScanResult scanResult) {
        tupleDefinitions.addAll(scanResult.tupleDefinitions);
        namedTupleDefinitions.addAll(scanResult.namedTupleDefinitions);
        return this;
    }

    public TupleDefinitionScanResult add(TupleDefinition tupleDefinition) {
        tupleDefinitions.add(tupleDefinition);
        return this;
    }

    public TupleDefinitionScanResult add(NamedTupleDefinition tupleDefinition) {
        namedTupleDefinitions.add(tupleDefinition);
        return this;
    }
}
