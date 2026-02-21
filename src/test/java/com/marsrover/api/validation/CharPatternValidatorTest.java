package com.marsrover.api.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CharPatternValidatorTest {

    private CharPatternValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new CharPatternValidator();
        CharPattern annotation = mock(CharPattern.class);
        when(annotation.regexp()).thenReturn("[NWSE]");
        validator.initialize(annotation);
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    void isValid_shouldReturnTrueForNullValue() {
        assertTrue(validator.isValid(null, context));
    }

    @Test
    void isValid_shouldReturnTrueForValidCharacter() {
        assertTrue(validator.isValid('N', context));
        assertTrue(validator.isValid('W', context));
        assertTrue(validator.isValid('S', context));
        assertTrue(validator.isValid('E', context));
    }

    @Test
    void isValid_shouldReturnFalseForInvalidCharacter() {
        assertFalse(validator.isValid('X', context));
        assertFalse(validator.isValid('Z', context));
    }
}
