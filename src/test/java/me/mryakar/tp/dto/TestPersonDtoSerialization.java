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
class TestPersonDtoSerialization {

    @Autowired
    private JacksonTester<PersonDto> json;

    static PersonDto initialDto;

    @BeforeAll
    static void prepareDto() {
        long id = 1L;
        String name = "name";
        String surname = "surname";
        int age = 1;
        Gender gender = Gender.MALE;
        String nation = "nation";
        initialDto = new PersonDto(id, name, surname, age, gender, nation);
    }

    @DisplayName("PersonDto should be serialized to JSON successfully.")
    @Test
    void Should_SuccessfullySerializeToJson_When_IfDtoIsValid() throws IOException {

        JsonContent<PersonDto> jsonContent = json.write(initialDto);

        assertThat(jsonContent)
                .isStrictlyEqualToJson("person_dto.json")
                .hasJsonPathNumberValue("@.id")
                .hasJsonPathStringValue("@.name")
                .hasJsonPathStringValue("@.surname")
                .hasJsonPathNumberValue("@.age")
                .hasJsonPathNumberValue("@.gender")
                .hasJsonPathStringValue("@.nation");

        assertThat(jsonContent).extractingJsonPathNumberValue("@.id").isEqualTo(initialDto.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.name").isEqualTo(initialDto.name());
        assertThat(jsonContent).extractingJsonPathStringValue("@.surname").isEqualTo(initialDto.surname());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.age").isEqualTo(initialDto.age());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.gender").isEqualTo(initialDto.gender().toValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.nation").isEqualTo(initialDto.nation());

    }

    @DisplayName("Person JSON should be deserialized to PersonDto successfully.")
    @Test
    void Should_SuccessfullyDeserializeToObject_When_IfPersonDtoJsonIsValid() throws IOException {
        String initialJson = """
                     {
                        "id": 1,
                        "name": "name",
                        "surname": "surname",
                        "age": 1,
                        "gender": 0,
                        "nation": "nation"
                      }
                """;

        PersonDto dto = json.parseObject(initialJson);

        assertThat(dto).isEqualTo(initialDto);
    }
}