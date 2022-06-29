package com.scl.gt.component;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class CustomRowProcessorTest {

    private final CustomRowProcessor customRowProcessor = new CustomRowProcessor();

    @Test
    void shouldReturnNullForEmptyString() {
        assertThat(customRowProcessor.processColumnItem("")).isNull();
    }

    @Test
    void shouldReturnNullForStringWithOnlyWhitespace() {
        assertThat(customRowProcessor.processColumnItem("   ")).isNull();
    }

    @ParameterizedTest
    @MethodSource("stringValueToTest")
    void shouldReturnTrimmedString(String inputValue, String expectedValue) {
        assertThat(customRowProcessor.processColumnItem(inputValue)).isEqualTo(expectedValue);
    }

    @Test
    void shouldConvertRowAndTrimWhitespaces() {
        String[] rowToProcess = {"", " ", " Test", "Test "};
        customRowProcessor.processRow(rowToProcess);
        assertThat(rowToProcess[0]).isNull();
        assertThat(rowToProcess[1]).isNull();
        assertThat(rowToProcess[2]).isEqualTo("Test");
        assertThat(rowToProcess[3]).isEqualTo("Test");
    }

    private static Stream<Arguments> stringValueToTest() {
        return Stream.of(Arguments.of(" Test with trimmed value", "Test with trimmed value"),
                         Arguments.of(("Test with trimmed value "), "Test with trimmed value"));
    }

}
