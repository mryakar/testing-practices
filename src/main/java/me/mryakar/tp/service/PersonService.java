package me.mryakar.tp.service;

import me.mryakar.tp.dto.NewPersonDto;
import me.mryakar.tp.dto.PersonDto;

import java.util.List;

public interface PersonService {
    Long create(NewPersonDto dto);

    List<PersonDto> read();

    void update(PersonDto dto);

    void delete(Long id);
}