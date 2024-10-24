package com.spacemedia.spaceship.exception.personalized;

public class SpaceshipNotFoundException extends RuntimeException{
    public SpaceshipNotFoundException(String message){
        super(message);
    }
}
