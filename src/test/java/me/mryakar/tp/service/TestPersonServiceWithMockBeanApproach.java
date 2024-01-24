package me.mryakar.tp.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import me.mryakar.tp.dao.PersonRepository;
import me.mryakar.tp.dto.NewPersonDto;
import me.mryakar.tp.dto.PersonDto;
import me.mryakar.tp.entity.Gender;
import me.mryakar.tp.entity.PersonEntity;
import me.mryakar.tp.mapper.NewPersonMapper;
import me.mryakar.tp.mapper.PersonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static me.mryakar.tp.service.PersonServiceImpl.ALREADY_EXISTED_PERSON_ERROR_MESSAGE;
import static me.mryakar.tp.service.PersonServiceImpl.NO_SUCH_PERSON_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = PersonServiceImpl.class)
class TestPersonServiceWithMockBeanApproach {

    @Autowired
    PersonServiceImpl service;

    @MockBean
    PersonRepository repository;

    @MockBean
    PersonMapper mapper;

    @MockBean
    NewPersonMapper newPersonMapper;

    @DisplayName("An id which is a Long should be returned after the successful creation of new Person.")
    @Test
    void Should_CreateAndSavePerson_When_NewPersonDtoIsValid() {
        NewPersonDto newPersonDto = new NewPersonDto("name", "surname", 1, Gender.MALE, "nation");
        PersonDto personDto = new PersonDto(1L, "name", "surname", 1, Gender.MALE, "nation");
        PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setName("name");
        entity.setSurname("surname");
        entity.setAge(1);
        entity.setGender(Gender.MALE);
        entity.setNation("nation");

        when(repository.existsByNameAndSurnameAndAge(newPersonDto.name(), newPersonDto.surname(), newPersonDto.age())).thenReturn(false);
        when(newPersonMapper.toEntity(newPersonDto)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(personDto);
        when(repository.save(entity)).thenReturn(entity);


        PersonDto createdPerson = service.create(newPersonDto);

        verify(repository).existsByNameAndSurnameAndAge(newPersonDto.name(), newPersonDto.surname(), newPersonDto.age());
        verify(newPersonMapper).toEntity(newPersonDto);
        verify(repository).save(entity);
        assertThat(createdPerson)
                .isNotNull()
                .isEqualTo(personDto);
    }

    @DisplayName("EntityExistsException should be thrown when trying to create an already existed Person.")
    @Test
    void Should_ThrowEntityExistException_When_CreatingAlreadyExistingPerson() {
        NewPersonDto newPersonDto = new NewPersonDto("name", "surname", 1, Gender.MALE, "nation");
        when(repository.existsByNameAndSurnameAndAge(newPersonDto.name(), newPersonDto.surname(), newPersonDto.age())).thenReturn(true);

        assertThrows(
                ALREADY_EXISTED_PERSON_ERROR_MESSAGE,
                EntityExistsException.class,
                () -> service.create(newPersonDto)
        );

        verify(repository).existsByNameAndSurnameAndAge(newPersonDto.name(), newPersonDto.surname(), newPersonDto.age());
    }

    @DisplayName("List of Person dtos should be returned when to try reading them.")
    @Test
    void Should_ReadAllPersons() {
        PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setName("name");
        entity.setSurname("surname");
        entity.setAge(1);
        entity.setGender(Gender.MALE);
        entity.setNation("nation");
        List<PersonEntity> entityList = List.of(entity);
        PersonDto dto = new PersonDto(1L, "name", "surname", 1, Gender.MALE, "nation");
        List<PersonDto> dtoList = List.of(dto);
        when(repository.findAll()).thenReturn(entityList);
        when(mapper.toDto(entityList)).thenReturn(dtoList);


        List<PersonDto> returnedDtoList = service.read();

        verify(repository).findAll();
        verify(mapper).toDto(entityList);
        assertThat(returnedDtoList)
                .isNotNull()
                .isEqualTo(dtoList);
    }

    @DisplayName("Person should be updated with new information.")
    @Test
    void Should_UpdatePerson_When_UpdatedPersonDtoIsPresent() {
        PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setName("name");
        entity.setSurname("surname");
        entity.setAge(1);
        entity.setGender(Gender.MALE);
        entity.setNation("nation");
        PersonDto updatedDto = new PersonDto(1L, "name", "surname", 1, Gender.MALE, "nation");
        when(repository.findById(updatedDto.id())).thenReturn(Optional.of(entity));

        service.update(updatedDto);

        verify(repository).findById(updatedDto.id());
        verify(mapper).updatePartially(updatedDto, entity);
    }

    @DisplayName("EntityNotFoundException should be thrown when trying to update a not existing Person.")
    @Test
    void Should_ThrowEntityNotFoundException_When_UpdatingNotExistingPerson() {
        PersonDto updatedDto = new PersonDto(1L, "name", "surname", 1, Gender.MALE, "nation");
        when(repository.findById(updatedDto.id())).thenReturn(Optional.empty());

        assertThrows(
                NO_SUCH_PERSON_ERROR_MESSAGE,
                EntityNotFoundException.class,
                () -> service.update(updatedDto)
        );

        verify(repository).findById(updatedDto.id());
    }

    @DisplayName("Person should be deleted with id information.")
    @Test
    void Should_DeletePerson_When_PersonDtoIsPresent() {
        PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setName("name");
        entity.setSurname("surname");
        entity.setAge(1);
        entity.setGender(Gender.MALE);
        entity.setNation("nation");
        PersonDto updatedDto = new PersonDto(1L, "name", "surname", 1, Gender.MALE, "nation");
        when(repository.findById(updatedDto.id())).thenReturn(Optional.of(entity));

        service.deleteById(updatedDto.id());

        verify(repository).findById(updatedDto.id());
        verify(repository).delete(entity);
    }

    @DisplayName("EntityNotFoundException should be thrown when trying to update a not existing Person.")
    @Test
    void Should_ThrowEntityNotFoundException_When_DeletingNotExistingPerson() {
        Long id = 1L;
        PersonDto updatedDto = new PersonDto(id, "name", "surname", 1, Gender.MALE, "nation");
        when(repository.findById(updatedDto.id())).thenReturn(Optional.empty());

        assertThrows(
                NO_SUCH_PERSON_ERROR_MESSAGE,
                EntityNotFoundException.class,
                () -> service.deleteById(id)
        );

        verify(repository).findById(updatedDto.id());
    }
}
