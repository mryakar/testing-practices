package me.mryakar.tp.rest;

import me.mryakar.tp.dto.PersonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(
        value = "/me/mryakar/tp/rest/sbt_schema.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
class TestPersonControllerWithSpringBootApproach {

    @Autowired
    TestRestTemplate restTemplate;

    @Sql(
            value = "/me/mryakar/tp/rest/sbt_data.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Test
    void Should_ReadAllPersons() {
        String getUrl = "/person";

        ResponseEntity<List<PersonDto>> responseEntity = restTemplate.exchange(
                getUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).hasSize(3);
    }
}