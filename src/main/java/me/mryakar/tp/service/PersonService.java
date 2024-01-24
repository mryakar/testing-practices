package me.mryakar.tp.service;

import me.mryakar.tp.dto.NewPersonDto;
import me.mryakar.tp.dto.PersonDto;

import java.util.List;

public interface PersonService {
    PersonDto create(NewPersonDto dto);

    List<PersonDto> read();

    void update(PersonDto dto);

    void deleteById(Long id);
}