package com.spacemedia.spaceship.controller;

import com.spacemedia.spaceship.entity.Spaceship;
import com.spacemedia.spaceship.exception.personalized.SpaceshipNotFoundException;
import com.spacemedia.spaceship.service.SpaceshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spaceships")
public class SpaceshipController {

    @Autowired
    private SpaceshipService spaceshipService;

    @Cacheable(value = "spaceships", key = "#page + '-' + #size")
    @GetMapping
    public Page<Spaceship> getAllSpaceships(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return spaceshipService.getAllSpaceships(pageRequest);
    }

    @Cacheable(value = "spaceships", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<Spaceship> getSpaceshipById(@PathVariable Long id){
        Optional<Spaceship> spaceship = spaceshipService.getSpaceshipById(id);
        if (!spaceship.isPresent()){
            throw new SpaceshipNotFoundException("Nave espacial con ID " + id + " no encontrada.");
        }
        return ResponseEntity.ok(spaceship.get());
    }

    @Cacheable(value = "spaceships", key = "#name")
    @GetMapping("/search")
    public ResponseEntity<List<Spaceship>> getSpaceshipByPartialName(@RequestParam String name){
        List<Spaceship> spaceships = spaceshipService.getSpaceshipByPartialName(name);
        if (spaceships.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spaceships);
    }

    @CacheEvict(value = "spaceships", allEntries = true)
    @PostMapping
    public ResponseEntity<Spaceship> createSpaceship(@RequestBody Spaceship spaceship){
        Spaceship spaceshipCreado = spaceshipService.saveSpaceship(spaceship);
        return ResponseEntity.ok(spaceshipCreado);
    }

    @CacheEvict(value = "spaceships", allEntries = true)
    @PutMapping
    public ResponseEntity<Spaceship> editSpaceship(@RequestBody Spaceship spaceship){
        Spaceship spaceshipModificado = spaceshipService.saveSpaceship(spaceship);
        return ResponseEntity.ok(spaceshipModificado);
    }

    @CacheEvict(value = "spaceships", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Spaceship> deleteSpaceship(@PathVariable Long id){
        Optional<Spaceship> spaceship = spaceshipService.getSpaceshipById(id);
        if(spaceship.isPresent()){
            spaceshipService.deleteSpaceship(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
