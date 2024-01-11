package me.mryakar.tp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.mryakar.tp.entity.Gender;

public record NewPersonDto(
        @NotBlank(message = "Name cannot be blank.")
        String name,
        @NotBlank(message = "Surname cannot be blank.")
        String surname,
        @Min(value = 0, message = "Age cannot be smaller than zero.")
        int age,
        @NotNull
        Gender gender,
        @NotBlank
        String nation
) {
}
