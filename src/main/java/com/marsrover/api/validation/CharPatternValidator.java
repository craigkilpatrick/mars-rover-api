package com.marsrover.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CharPatternValidator implements ConstraintValidator<CharPattern, Character> {
  private Pattern pattern;

  @Override
  public void initialize(CharPattern constraintAnnotation) {
    this.pattern = Pattern.compile(constraintAnnotation.regexp());
  }

  @Override
  public boolean isValid(Character value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // @NotNull should handle null checks
    }
    return pattern.matcher(String.valueOf(value)).matches();
  }
}
