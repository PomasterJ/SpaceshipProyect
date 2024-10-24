package com.spacemedia.spaceship.repository;

import com.spacemedia.spaceship.entity.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {

    List<Spaceship> findByName(String name);

    @Query("SELECT s from Spaceship s where UPPER(s.name) LIKE %:name%")
    List<Spaceship> findByPartialName(@Param("name")String name);
}
