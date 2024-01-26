package me.mryakar.tp.entity;

import jakarta.validation.ConstraintViolationException;
import me.mryakar.tp.dao.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class TestPersistPersonEntityWithSliceApproach {

    @Autowired
    PersonRepository repository;

    PersonEntity entity;

    @BeforeEach
    void prepareEntity() {
        entity = new PersonEntity();
        entity.setName("name");
        entity.setName("surname");
        entity.setAge(1);
        entity.setGender(Gender.MALE);
        entity.setNation("nation");
    }

    @DisplayName("PersonEntity should be persisted to database ")
    @Test
    void Should_SuccessfullyPersistPersonEntity_When_IfEntityIsValid() {
        assertDoesNotThrow(() -> repository.save(entity));
    }

    @DisplayName("It should fail to persist a PersonEntity with a blank name field and throw a ConstraintViolationException.")
    @Test
    void Should_FailToPersistPersonEntityAndThrowException_When_NameIsBlankOrNull() {
        entity.setName("");

        assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(entity));
    }

    @DisplayName("It should fail to persist a PersonEntity with a null name field and throw a ConstraintViolationException.")
    @Test
    void Should_FailToPersistPersonEntityAndThrowException_When_SurnameNameIsBlankOrNull() {
        entity.setSurname("");

        assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(entity));
    }

    @DisplayName("It should fail to persist a PersonEntity with an invalid age field and throw a ConstraintViolationException.")
    @Test
    void Should_FailToPersistPersonEntityAndThrowException_When_AgeIsInvalid() {
        entity.setAge(-1);
        assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(entity));
        entity.setAge(131);
        assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(entity));
    }

    @DisplayName("It should fail to persist a PersonEntity with an invalid nation field and throw a ConstraintViolationException.")
    @Test
    void Should_FailToPersistPersonEntityAndThrowException_When_NationIsInvalid() {
        entity.setNation("");
        assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(entity));
        entity.setNation("1");
        assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(entity));
        entity.setNation("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertThrows(ConstraintViolationException.class, () -> repository.saveAndFlush(entity));
    }
}