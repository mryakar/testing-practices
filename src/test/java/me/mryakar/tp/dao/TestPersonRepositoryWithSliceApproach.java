package me.mryakar.tp.dao;

import me.mryakar.tp.entity.PersonEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=none"
})
@Sql(
        value = "/me/mryakar/tp/rest/sbt_schema.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
class TestPersonRepositoryWithSliceApproach {

    @Autowired
    PersonRepository repository;

    @Sql(
            value = "/me/mryakar/tp/rest/sbt_data.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Test
    void Should_ReadAll() {
        List<PersonEntity> persons = repository.findAll();
        assertThat(persons).hasSize(3);
    }

}
