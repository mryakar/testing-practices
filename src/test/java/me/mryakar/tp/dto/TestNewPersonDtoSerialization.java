package me.mryakar.tp.dto;

import me.mryakar.tp.entity.Gender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
 class TestNewPersonDtoSerialization {

    @Autowired
    private JacksonTester<NewPersonDto> json;

    static NewPersonDto initialDto;

    @BeforeAll
    static void prepareDto() {
        String name = "name";
        String surname = "surname";
        int age = 1;
        Gender gender = Gender.MALE;
        String nation = "nation";
        initialDto = new NewPersonDto(name, surname, age, gender, nation);
    }

    @DisplayName("NewPersonDto should be serialized to JSON successfully.")
    @Test
    void Should_SuccessfullySerializeToJson_When_IfDtoIsValid() throws IOException {

        JsonContent<NewPersonDto> jsonContent = json.write(initialDto);

        assertThat(jsonContent)
                .isStrictlyEqualToJson("new_person_dto.json")
                .hasJsonPathStringValue("@.name")
                .hasJsonPathStringValue("@.surname")
                .hasJsonPathNumberValue("@.age")
                .hasJsonPathNumberValue("@.gender")
                .hasJsonPathStringValue("@.nation");

        assertThat(jsonContent).extractingJsonPathStringValue("@.name").isEqualTo(initialDto.name());
        assertThat(jsonContent).extractingJsonPathStringValue("@.surname").isEqualTo(initialDto.surname());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.age").isEqualTo(initialDto.age());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.gender").isEqualTo(initialDto.gender().toValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.nation").isEqualTo(initialDto.nation());

    }

    @DisplayName("A valid JSON for a new person should be deserialized to NewPersonDto successfully.")
    @Test
    void Should_SuccessfullyDeserializeToNewPersonDto_IfInputJsonIsValid() throws IOException {
        String initialJson = """
                     {
                        "name": "name",
                        "surname": "surname",
                        "age": 1,
                        "gender": 0,
                        "nation": "nation"
                      }
                """;

        NewPersonDto dto = json.parseObject(initialJson);

        assertThat(dto).isEqualTo(initialDto);
    }
}
