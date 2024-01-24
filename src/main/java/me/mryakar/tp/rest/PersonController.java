package me.mryakar.tp.rest;

import lombok.AllArgsConstructor;
import me.mryakar.tp.dto.NewPersonDto;
import me.mryakar.tp.dto.PersonDto;
import me.mryakar.tp.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    private final PersonService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody NewPersonDto newPersonDto) {
        PersonDto dto = service.create(newPersonDto);

        URI resourceLocation = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{resourceId}")
                .buildAndExpand(dto.id())
                .toUri();

        return ResponseEntity.created(resourceLocation).build();
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> read() {
        return ResponseEntity.ok(service.read());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody PersonDto updatedPersonDto) {
        service.update(updatedPersonDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

}