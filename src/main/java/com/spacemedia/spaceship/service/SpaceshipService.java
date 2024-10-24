package com.spacemedia.spaceship.service;

import com.spacemedia.spaceship.entity.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface SpaceshipService {

    public Page<Spaceship> getAllSpaceships(PageRequest pageRequest);

    public Optional<Spaceship> getSpaceshipById(Long id);

    public List<Spaceship> getSpaceshipByName(String name);

    public List<Spaceship> getSpaceshipByPartialName(String name);

    public Spaceship saveSpaceship (Spaceship spaceship);

    public void deleteSpaceship(Long id);
}
