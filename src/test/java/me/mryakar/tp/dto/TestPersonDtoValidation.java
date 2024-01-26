package me.mryakar.tp.dto;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import me.mryakar.tp.entity.Gender;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class TestPersonDtoValidation {

    private static Validator validator;
    private static ValidatorFactory factory;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void close() {
        factory.close();
    }

    @Test
    void Should_Validate_When_PersonDtoIsValid() {
        PersonDto personDto = new PersonDto(1L, "John", "Doe", 25, Gender.MALE, "US");

        Set<ConstraintViolation<PersonDto>> violations = validator.validate(personDto);

        assertThat(violations).hasSize(0);
    }

}