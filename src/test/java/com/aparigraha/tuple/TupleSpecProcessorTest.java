package com.aparigraha.tuple;

import com.aparigraha.tuple.generator.TupleGenerationParams;
import com.aparigraha.tuple.generator.TupleGenerator;
import com.aparigraha.tuple.generator.TupleSchema;
import com.aparigraha.tuple.generator.TupleSchemaWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TupleSpecProcessorTest {
    @Mock
    private TupleGenerator tupleGenerator;

    @Mock
    private TupleSchemaWriter tupleSchemaWriter;

    @Spy
    private RoundEnvironment roundEnvironment;

    private TypeElement mockTupleSpecAnnotation = mockAnnotation("com.aparigraha.tuple.TupleSpec");

    private TypeElement mockNamedTupleSpecAnnotation = mockAnnotation("com.aparigraha.tuple.NamedTupleSpec");


    @Test
    void shouldGenerateTuplesWhenAnnotationsAreAreDefinedAcrossMultipleElements() throws IOException {
        var tupleSpec1 = mock(TupleSpec.class);
        when(tupleSpec1.value())
                .thenReturn(new int[] {2, 4});

        var tupleSpec2 = mock(TupleSpec.class);
        when(tupleSpec2.value())
                .thenReturn(new int[] {1, 3});

        var element1 = mock(VariableElement.class);
        when(element1.getAnnotation(TupleSpec.class)).thenReturn(tupleSpec1);

        var element2 = mock(VariableElement.class);
        when(element2.getAnnotation(TupleSpec.class)).thenReturn(tupleSpec2);

        when(tupleGenerator.generate(any())).thenAnswer(invocation -> {
            TupleGenerationParams params = invocation.getArgument(0);

            if ("Tuple4".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0", "item1", "item2", "item3"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple4",
                        "Tuple code for Tuple4"
                );
            }
            else if ("Tuple2".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0", "item1"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple2",
                        "Tuple code for Tuple2"
                );
            }
            else if ("Tuple3".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0", "item1", "item2"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple3",
                        "Tuple code for Tuple3"
                );
            }
            else if ("Tuple1".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple1",
                        "Tuple code for Tuple1"
                );
            }
            else return null;
        });

        doReturn(Set.of(element1, element2))
                .when(roundEnvironment)
                .getElementsAnnotatedWith(mockTupleSpecAnnotation);

        TupleSpecProcessor tupleSpecProcessor = new TupleSpecProcessor(tupleGenerator, tupleSchemaWriter);

        assertTrue(tupleSpecProcessor.process(Set.of(mockTupleSpecAnnotation), roundEnvironment));
        verify(tupleSchemaWriter).write(eq("Tuple code for Tuple4"), eq("com.aparigraha.tuples.Tuple4"), any());
        verify(tupleSchemaWriter).write(eq("Tuple code for Tuple2"), eq("com.aparigraha.tuples.Tuple2"), any());
        verify(tupleSchemaWriter).write(eq("Tuple code for Tuple3"), eq("com.aparigraha.tuples.Tuple3"), any());
        verify(tupleSchemaWriter).write(eq("Tuple code for Tuple1"), eq("com.aparigraha.tuples.Tuple1"), any());
    }


    @Test
    void shouldGenerateUniqueTuplesWhenAnnotationsAreAreDefinedAcrossMultipleElements() throws IOException {
        var tupleSpec1 = mock(TupleSpec.class);
        when(tupleSpec1.value())
                .thenReturn(new int[] {1, 2, 1, 4});

        var tupleSpec2 = mock(TupleSpec.class);
        when(tupleSpec2.value())
                .thenReturn(new int[] {3, 4, 3, 4});

        var tupleSpec3 = mock(NamedTupleSpec.class);
        var classSpec1 = mock(ClassSpec.class);
        when(classSpec1.className()).thenReturn("StudentInfo");
        when(classSpec1.fields()).thenReturn(new String[] { "Name" });
        var classSpec2 = mock(ClassSpec.class);
        when(classSpec2.className()).thenReturn("Tuple2");
        when(classSpec2.fields()).thenReturn(new String[] { "item0", "item1" });
        var classSpec3 = mock(ClassSpec.class);
        when(classSpec3.className()).thenReturn("StudentInfo");
        when(classSpec3.fields()).thenReturn(new String[] { "Name" });
        when(tupleSpec3.value()).thenReturn(new ClassSpec[] { classSpec1, classSpec2, classSpec3 });


        var tupleSpec4 = mock(NamedTupleSpec.class);
        var classSpec4 = mock(ClassSpec.class);
        when(classSpec4.className()).thenReturn("DepartmentInfo");
        when(classSpec4.fields()).thenReturn(new String[] { "Name" });
        var classSpec5 = mock(ClassSpec.class);
        when(classSpec5.className()).thenReturn("Tuple2");
        when(classSpec5.fields()).thenReturn(new String[] { "item0", "item1" });
        var classSpec6 = mock(ClassSpec.class);
        when(classSpec6.className()).thenReturn("DepartmentInfo");
        when(classSpec6.fields()).thenReturn(new String[] { "Name" });
        when(tupleSpec4.value()).thenReturn(new ClassSpec[] { classSpec4, classSpec5, classSpec6 });


        var element1 = mock(VariableElement.class);
        when(element1.getAnnotation(TupleSpec.class)).thenReturn(tupleSpec1);
        when(element1.getAnnotation(NamedTupleSpec.class)).thenReturn(tupleSpec3);

        var element2 = mock(VariableElement.class);
        when(element2.getAnnotation(TupleSpec.class)).thenReturn(tupleSpec2);
        when(element2.getAnnotation(NamedTupleSpec.class)).thenReturn(tupleSpec4);

        when(tupleGenerator.generate(any())).thenAnswer(invocation -> {
            TupleGenerationParams params = invocation.getArgument(0);

            if ("Tuple4".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0", "item1", "item2", "item3"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple4",
                        "Tuple code for Tuple4"
                );
            }
            else if ("Tuple2".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0", "item1"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple2",
                        "Tuple code for Tuple2"
                );
            }
            else if ("Tuple3".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0", "item1", "item2"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple3",
                        "Tuple code for Tuple3"
                );
            }
            else if ("Tuple1".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple1",
                        "Tuple code for Tuple1"
                );
            }
            else if ("StudentInfo".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("Name"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "StudentInfo",
                        "Tuple code for StudentInfo"
                );
            }
            else if ("DepartmentInfo".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("Name"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "DepartmentInfo",
                        "Tuple code for DepartmentInfo"
                );
            }
            else return null;
        });

        doReturn(Set.of(element1, element2))
                .when(roundEnvironment)
                .getElementsAnnotatedWith(mockTupleSpecAnnotation);
        doReturn(Set.of())
                .when(roundEnvironment)
                .getElementsAnnotatedWith(mockNamedTupleSpecAnnotation);

        TupleSpecProcessor tupleSpecProcessor = new TupleSpecProcessor(tupleGenerator, tupleSchemaWriter);

        assertTrue(tupleSpecProcessor.process(Set.of(mockTupleSpecAnnotation, mockNamedTupleSpecAnnotation), roundEnvironment));
        verify(tupleSchemaWriter).write(eq("Tuple code for Tuple4"), eq("com.aparigraha.tuples.Tuple4"), any());
        verify(tupleSchemaWriter).write(eq("Tuple code for Tuple2"), eq("com.aparigraha.tuples.Tuple2"), any());
        verify(tupleSchemaWriter).write(eq("Tuple code for Tuple3"), eq("com.aparigraha.tuples.Tuple3"), any());
        verify(tupleSchemaWriter).write(eq("Tuple code for Tuple1"), eq("com.aparigraha.tuples.Tuple1"), any());
        verify(tupleSchemaWriter).write(eq("Tuple code for StudentInfo"), eq("com.aparigraha.tuples.StudentInfo"), any());
        verify(tupleSchemaWriter).write(eq("Tuple code for DepartmentInfo"), eq("com.aparigraha.tuples.DepartmentInfo"), any());
    }


    @Test
    void shouldHandleGenerationFails() throws IOException {
        var tupleSpec1 = mock(TupleSpec.class);
        when(tupleSpec1.value())
                .thenReturn(new int[] {2, 4});

        var element1 = mock(VariableElement.class);
        when(element1.getAnnotation(TupleSpec.class)).thenReturn(tupleSpec1);


        when(tupleGenerator.generate(any())).thenAnswer(invocation -> {
            TupleGenerationParams params = invocation.getArgument(0);

            if ("Tuple4".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0", "item1", "item2", "item3"))
            ) {
                throw new IOException();
            }
            else if ("Tuple2".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0", "item1"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple2",
                        "Tuple code for Tuple2"
                );
            }
            else return null;
        });

        doReturn(Set.of(element1))
                .when(roundEnvironment)
                .getElementsAnnotatedWith(mockTupleSpecAnnotation);

        TupleSpecProcessor tupleSpecProcessor = new TupleSpecProcessor(tupleGenerator, tupleSchemaWriter);

        assertTrue(tupleSpecProcessor.process(Set.of(mockTupleSpecAnnotation), roundEnvironment));
        verify(tupleSchemaWriter).write(eq("Tuple code for Tuple2"), eq("com.aparigraha.tuples.Tuple2"), any());
        verify(tupleSchemaWriter, times(0)).write(eq("Tuple code for Tuple4"), eq("com.aparigraha.tuples.Tuple4"), any());
    }


    @Test
    void shouldHandleFileWriteFails() throws IOException {
        var tupleSpec1 = mock(TupleSpec.class);
        when(tupleSpec1.value())
                .thenReturn(new int[] {2, 4});

        var element1 = mock(VariableElement.class);
        when(element1.getAnnotation(TupleSpec.class)).thenReturn(tupleSpec1);

        when(tupleGenerator.generate(any())).thenAnswer(invocation -> {
            TupleGenerationParams params = invocation.getArgument(0);

            if ("Tuple4".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0", "item1", "item2", "item3"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple4",
                        "Tuple code for Tuple4"
                );
            }
            else if ("Tuple2".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0", "item1"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple2",
                        "Tuple code for Tuple2"
                );
            }
            else return null;
        });

        doThrow(new IOException())
                .when(tupleSchemaWriter).write(eq("Tuple code for Tuple4"), eq("com.aparigraha.tuples.Tuple4"), any());
        doAnswer(invocationOnMock -> null)
                .when(tupleSchemaWriter).write(eq("Tuple code for Tuple2"), eq("com.aparigraha.tuples.Tuple2"), any());

        doReturn(Set.of(element1))
                .when(roundEnvironment)
                .getElementsAnnotatedWith(mockTupleSpecAnnotation);

        TupleSpecProcessor tupleSpecProcessor = new TupleSpecProcessor(tupleGenerator, tupleSchemaWriter);

        assertTrue(tupleSpecProcessor.process(Set.of(mockTupleSpecAnnotation), roundEnvironment));
        verify(tupleSchemaWriter).write(eq("Tuple code for Tuple2"), eq("com.aparigraha.tuples.Tuple2"), any());
        verify(tupleSchemaWriter).write(eq("Tuple code for Tuple4"), eq("com.aparigraha.tuples.Tuple4"), any());
    }


    @Test
    void shouldSkipTupleGenerationAndReturnFalseIfAnnotationDoesntMatchTupleSpec() throws IOException {
        TupleSpecProcessor tupleSpecProcessor = new TupleSpecProcessor(tupleGenerator, tupleSchemaWriter);

        assertFalse(tupleSpecProcessor.process(Set.of(), roundEnvironment));
        verify(tupleGenerator, times(0)).generate(any());
        verify(tupleSchemaWriter, times(0)).write(any(), any(), any());
    }


    @Test
    void shouldGenerateNamedTuplesWhenAnnotationsAreAreDefinedAcrossMultipleElements() throws IOException {
        var tupleSpec1 = mock(NamedTupleSpec.class);
        var classSpec1 = mock(ClassSpec.class);
        when(classSpec1.className())
                .thenReturn("StudentInfo");
        when(classSpec1.fields())
                .thenReturn(new String[] { "Name", "Age", "isDayScholar" });
        when(tupleSpec1.value()).thenReturn(new ClassSpec[] { classSpec1 });

        var tupleSpec2 = mock(NamedTupleSpec.class);
        var classSpec2 = mock(ClassSpec.class);
        when(classSpec2.className())
                .thenReturn("DepartmentInfo");
        when(classSpec2.fields())
                .thenReturn(new String[] { "Name" });
        var classSpec3 = mock(ClassSpec.class);
        when(classSpec3.className())
                .thenReturn("StaffInfo");
        when(classSpec3.fields())
                .thenReturn(new String[] { "Name", "Age" });

        when(tupleSpec2.value()).thenReturn(new ClassSpec[] { classSpec2, classSpec3 });

        var element1 = mock(VariableElement.class);
        when(element1.getAnnotation(NamedTupleSpec.class)).thenReturn(tupleSpec1);
        when(element1.getAnnotation(TupleSpec.class)).thenReturn(null);

        var element2 = mock(VariableElement.class);
        when(element2.getAnnotation(NamedTupleSpec.class)).thenReturn(tupleSpec2);
        when(element2.getAnnotation(TupleSpec.class)).thenReturn(null);

        when(tupleGenerator.generate(any())).thenAnswer(invocation -> {
            TupleGenerationParams params = invocation.getArgument(0);

            if ("StudentInfo".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("Name", "Age", "isDayScholar"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "StudentInfo",
                        "Tuple code for StudentInfo"
                );
            }
            else if ("DepartmentInfo".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("Name"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "DepartmentInfo",
                        "Tuple code for DepartmentInfo"
                );
            }
            else if ("StaffInfo".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("Name", "Age"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "StaffInfo",
                        "Tuple code for StaffInfo"
                );
            }
            else return null;
        });

        doReturn(Set.of(element1, element2))
                .when(roundEnvironment)
                .getElementsAnnotatedWith(mockNamedTupleSpecAnnotation);

        doReturn(Set.of())
                .when(roundEnvironment)
                .getElementsAnnotatedWith(mockTupleSpecAnnotation);

        TupleSpecProcessor tupleSpecProcessor = new TupleSpecProcessor(tupleGenerator, tupleSchemaWriter);

        assertTrue(tupleSpecProcessor.process(Set.of(mockTupleSpecAnnotation, mockNamedTupleSpecAnnotation), roundEnvironment));
        verify(tupleSchemaWriter).write(eq("Tuple code for StudentInfo"), eq("com.aparigraha.tuples.StudentInfo"), any());
        verify(tupleSchemaWriter).write(eq("Tuple code for DepartmentInfo"), eq("com.aparigraha.tuples.DepartmentInfo"), any());
        verify(tupleSchemaWriter).write(eq("Tuple code for StaffInfo"), eq("com.aparigraha.tuples.StaffInfo"), any());
    }


    @Test
    void shouldGenerateNamedTuplesWhenAnnotationsAreAreDefinedOnTheSameElement() throws IOException {
        var tupleSpec = mock(TupleSpec.class);
        when(tupleSpec.value())
                .thenReturn(new int[] { 1 });

        var namedTupleSpec = mock(NamedTupleSpec.class);
        var classSpec = mock(ClassSpec.class);
        when(classSpec.className())
                .thenReturn("DepartmentInfo");
        when(classSpec.fields())
                .thenReturn(new String[] { "Name" });
        when(namedTupleSpec.value()).thenReturn(new ClassSpec[] { classSpec });

        var element = mock(VariableElement.class);
        when(element.getAnnotation(NamedTupleSpec.class)).thenReturn(namedTupleSpec);
        when(element.getAnnotation(TupleSpec.class)).thenReturn(tupleSpec);

        when(tupleGenerator.generate(any())).thenAnswer(invocation -> {
            TupleGenerationParams params = invocation.getArgument(0);

            if ("Tuple1".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("item0"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "Tuple1",
                        "Tuple code for Tuple1"
                );
            }
            else if ("DepartmentInfo".equals(params.className()) &&
                    "com.aparigraha.tuples".equals(params.packageName()) &&
                    Objects.equals(params.fields(), List.of("Name"))
            ) {
                return new TupleSchema(
                        "com.aparigraha.tuples",
                        "DepartmentInfo",
                        "Tuple code for DepartmentInfo"
                );
            }
            else return null;
        });

        doReturn(Set.of(element))
                .when(roundEnvironment)
                .getElementsAnnotatedWith(mockNamedTupleSpecAnnotation);

        doReturn(Set.of(element))
                .when(roundEnvironment)
                .getElementsAnnotatedWith(mockTupleSpecAnnotation);

        TupleSpecProcessor tupleSpecProcessor = new TupleSpecProcessor(tupleGenerator, tupleSchemaWriter);

        assertTrue(tupleSpecProcessor.process(Set.of(mockTupleSpecAnnotation, mockNamedTupleSpecAnnotation), roundEnvironment));
        verify(tupleSchemaWriter, times(1)).write(eq("Tuple code for Tuple1"), eq("com.aparigraha.tuples.Tuple1"), any());
        verify(tupleSchemaWriter, times(1)).write(eq("Tuple code for DepartmentInfo"), eq("com.aparigraha.tuples.DepartmentInfo"), any());
    }


    private TypeElement mockAnnotation(String className) {
        var annotation = mock(TypeElement.class);
        var name = mock(Name.class);
        when(name.toString()).thenReturn(className);
        when(annotation.getQualifiedName()).thenReturn(name);
        return annotation;
    }
}