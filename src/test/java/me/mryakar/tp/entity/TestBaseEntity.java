package me.mryakar.tp.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestBaseEntity {

    private static class ConcreteBaseEntity extends BaseEntity {}

    @Test
    @DisplayName("Two identical base entities must be equal and have same hashcode respect to their ids.")
    void Should_BeEqualAndHaveSameHashcode_When_CompareTwoPersonEntitiesWithSameId() {
        BaseEntity e1 = new ConcreteBaseEntity();
        e1.setId(1L);

        BaseEntity e2 = new ConcreteBaseEntity();
        e2.setId(1L);

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
