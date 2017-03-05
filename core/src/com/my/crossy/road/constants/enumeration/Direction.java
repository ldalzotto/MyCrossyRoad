package com.my.crossy.road.constants.enumeration;

/**
 * Représente la direction de déplacement du joueur sur l'environnement
 * Created by ldalzotto on 05/03/2017.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction getOpposite(Direction direction){
        Direction oppositeDirection = null;
        switch (direction){
            case UP:
                oppositeDirection = DOWN;
                break;
            case DOWN:
                oppositeDirection = UP;
                break;
            case LEFT:
                oppositeDirection = RIGHT;
                break;
            case RIGHT:
                oppositeDirection = LEFT;
                break;
        }
        return oppositeDirection;
    }
}
