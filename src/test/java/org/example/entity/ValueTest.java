package org.example.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.example.entity.Value.getValueByChar;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ValueTest {


    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void getValueByCharTest(String glif, String glifValue) {
        Value actualValue = Value.valueOf(glifValue);
        assertEquals(actualValue, getValueByChar(glif));
    }

}