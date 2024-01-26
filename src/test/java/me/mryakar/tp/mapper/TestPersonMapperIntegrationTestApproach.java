package me.mryakar.tp.mapper;

import me.mryakar.tp.dto.PersonDto;
import me.mryakar.tp.entity.Gender;
import me.mryakar.tp.entity.PersonEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TestPersonMapperIntegrationTestApproach {

    @Autowired
    PersonMapper mapper;
    static PersonEntity entity;
    static PersonDto dto;

    @BeforeAll
    static void prepare() {
        entity = new PersonEntity();
        entity.setId(1L);
        entity.setName("name");
        entity.setSurname("surname");
        entity.setAge(1);
        entity.setGender(Gender.MALE);
        entity.setNation("nation");

        dto = new PersonDto(
                1L,
                "name",
                "surname",
                1,
                Gender.MALE,
                "nation"
        );
    }

    @DisplayName("PersonEntity should be mapped successfully to PersonDto.")
    @Test
    void Should_MapPersonEntityToPersonDto_When_PersonEntityIsValid() {
        PersonDto mappedDto = mapper.toDto(entity);

        assertEquals(dto, mappedDto);
    }

    @DisplayName("PersonDto should be mapped successfully to PersonEntity.")
    @Test
    void Should_MapPersonEntityToPersonEntity_When_PersonDtoIsValid() {
        PersonEntity mappedEntity = mapper.toEntity(dto);

        assertEquals(entity.getId(), mappedEntity.getId());
        assertEquals(entity.getName(), mappedEntity.getName());
        assertEquals(entity.getSurname(), mappedEntity.getSurname());
        assertEquals(entity.getAge(), mappedEntity.getAge());
        assertEquals(entity.getGender(), mappedEntity.getGender());
        assertEquals(entity.getNation(), mappedEntity.getNation());
    }
}
