package com.spacemedia.spaceship.service.Impl;

import com.spacemedia.spaceship.entity.Spaceship;
import com.spacemedia.spaceship.repository.SpaceshipRepository;
import com.spacemedia.spaceship.service.SpaceshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceshipServiceImpl implements SpaceshipService {

    @Autowired
    SpaceshipRepository spaceshipRepository;

    @Override
    public Page<Spaceship> getAllSpaceships(PageRequest pageRequest) {
        return spaceshipRepository.findAll(pageRequest);
    }

    public Optional<Spaceship> getSpaceshipById(Long id){
        return spaceshipRepository.findById(id);
    }

    public List<Spaceship> getSpaceshipByName(String name){
        return spaceshipRepository.findByName(name);
    }

    @Override
    public List<Spaceship> getSpaceshipByPartialName(String name) {
        return spaceshipRepository.findByPartialName(name.toUpperCase());
    }

    public Spaceship saveSpaceship (Spaceship spaceship){
        return spaceshipRepository.save(spaceship);
    }

    public void deleteSpaceship(Long id){
        spaceshipRepository.deleteById(id);
    }


}
