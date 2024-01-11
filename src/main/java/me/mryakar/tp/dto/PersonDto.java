package me.mryakar.tp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.mryakar.tp.entity.Gender;

import java.io.Serializable;


public record PersonDto(
        @NotNull @Min(value = 1, message = "ID cannot be smaller than 1.")
        Long id,
        @NotBlank(message = "Name cannot be blank.")
        String name,
        @NotBlank(message = "Surname cannot be blank.")
        String surname,
        @Min(value = 0, message = "Age cannot be smaller than zero.")
        int age,
        @NotNull
        Gender gender,

        String nation
) implements Serializable {
}