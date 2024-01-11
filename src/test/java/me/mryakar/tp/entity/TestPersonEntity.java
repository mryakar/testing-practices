package me.mryakar.tp.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestPersonEntity {

    @Test
    @DisplayName("Two identical person entities must be equal and have same hashcode respect to their ids.")
    void Should_BeEqualAndHaveSameHashcode_When_CompareTwoPersonEntitiesWithSameId() {
        PersonEntity e1 = new PersonEntity();
        e1.setId(1L);
        e1.setName("name1");
        e1.setSurname("surname1");
        e1.setAge(1);
        e1.setGender(Gender.MALE);

        PersonEntity e2 = new PersonEntity();
        e2.setId(1L);
        e2.setName("name2");
        e2.setSurname("surasdadname2");
        e2.setAge(2);
        e2.setGender(Gender.FEMALE);
        e2.setNation("nation2");

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
