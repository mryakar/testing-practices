package me.mryakar.tp.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import me.mryakar.tp.dao.PersonRepository;
import me.mryakar.tp.dto.NewPersonDto;
import me.mryakar.tp.dto.PersonDto;
import me.mryakar.tp.entity.PersonEntity;
import me.mryakar.tp.mapper.NewPersonMapper;
import me.mryakar.tp.mapper.PersonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    public static final String ALREADY_EXISTED_PERSON_ERROR_MESSAGE = "Person already exists in the data source!";
    public static final String NO_SUCH_PERSON_ERROR_MESSAGE = "There is no such person in the data source!";

    private final PersonRepository repository;
    private final PersonMapper mapper;
    private final NewPersonMapper newPersonMapper;

    @Transactional
    @Override
    public PersonDto create(NewPersonDto dto) {
        boolean existsInDataSource = repository.existsByNameAndSurnameAndAge(dto.name(), dto.surname(), dto.age());
        if (existsInDataSource) {
            throw new EntityExistsException(ALREADY_EXISTED_PERSON_ERROR_MESSAGE);
        }

        PersonEntity entity = newPersonMapper.toEntity(dto);
        repository.save(entity);

        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PersonDto> read() {
        return mapper.toDto(repository.findAll());
    }

    @Transactional
    @Override
    public void update(PersonDto dto) {
        PersonEntity entity = repository.findById(dto.id()).orElseThrow(() -> new EntityNotFoundException(NO_SUCH_PERSON_ERROR_MESSAGE));
        mapper.updatePartially(dto, entity);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        PersonEntity entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NO_SUCH_PERSON_ERROR_MESSAGE));
        repository.delete(entity);
    }
}