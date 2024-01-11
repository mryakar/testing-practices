package me.mryakar.tp.dao;

import me.mryakar.tp.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    boolean existsByNameAndSurnameAndAge(String name, String surname, int age);
}