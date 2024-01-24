package me.mryakar.tp.rest;

import me.mryakar.tp.dto.NewPersonDto;
import me.mryakar.tp.dto.PersonDto;
import me.mryakar.tp.entity.Gender;
import me.mryakar.tp.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class TestPersonControllerWithSliceApproach {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService service;

    @Test
    void Should_CreateNewPerson_IfNewPersonDtoIsValid() throws Exception {
        PersonDto createdPerson = new PersonDto(1L, "name", "surname", 1, Gender.MALE, "nation");
        when(service.create(any(NewPersonDto.class))).thenReturn(createdPerson);
        String requestBody = "{ \"name\": \"name\", \"surname\": \"surname\", \"age\": 1, \"gender\": 0, \"nation\": \"nation\"}";

        mockMvc.perform(post("/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/person/1"))
                .andExpect(redirectedUrl("http://localhost/person/1"));

        verify(service).create(any(NewPersonDto.class));
    }

    @Test
    void Should_ReadAllPersons() throws Exception {
        List<PersonDto> personDtoList = List.of(new PersonDto(1L, "name", "surname", 1, Gender.MALE, "nation"));
        when(service.read()).thenReturn(personDtoList);

        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("name"))
                .andExpect(jsonPath("$.[0].surname").value("surname"))
                .andExpect(jsonPath("$.[0].age").value(1))
                .andExpect(jsonPath("$.[0].gender").value(0))
                .andExpect(jsonPath("$.[0].nation").value("nation"));
        verify(service).read();
    }

    @Test
    void Should_UpdatePerson_IfPersonDtoIsValid() throws Exception {
        doNothing().when(service).update(any(PersonDto.class));
        String requestBody = "{ \"name\": \"name\", \"surname\": \"surname\", \"age\": 1, \"gender\": 0, \"nation\": \"nation\"}";

        mockMvc.perform(put("/person")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(service).update(any(PersonDto.class));
    }

    @Test
    void Should_DeletePerson_IfPersonIsPresent() throws Exception {
        doNothing().when(service).deleteById(any(Long.class));

        mockMvc.perform(delete("/person/1"))
                .andExpect(status().isNoContent());

        verify(service).deleteById(any(Long.class));
    }
}
