package com.my.crossy.road.component.util;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.screen.util.MovePositionHandler;

/**
 * Created by ldalzotto on 02/04/2017.
 */
public class MovePositionHandlerCreator {

    private MovePositionHandlerCreator() throws InstantiationException{
        throw new InstantiationException("This class cannot be instatiated.");
    }

    /**
     * Generate a {@link MovePositionHandler} based on the direction and the actual {@link ModelInstance}
     * @param direction the direction of move of the 3D model
     * @param model3D the model to move
     * @return the position handler and its workflow
     */
    public static MovePositionHandler createHandler(Direction direction, ModelInstance model3D) {
        Vector3 endPositionMovement;
        Vector3 displacementVector;
        //récupération de la position actuelle
        endPositionMovement = model3D.transform.getTranslation(new Vector3());
        Vector3 speedVector = new Vector3(Configuration.ENVIRONNEMENT_SPEED.get_valeur(), 0 ,
                Configuration.ENVIRONNEMENT_SPEED.get_valeur());
        switch (direction){
            case UP:
                displacementVector = new Vector3(0, 0,-Configuration.TAILLE_BLOC.get_valeur());
                endPositionMovement.add(displacementVector);
                break;
            case DOWN:
                displacementVector = new Vector3(0, 0, Configuration.TAILLE_BLOC.get_valeur());
                endPositionMovement.add(displacementVector);
                break;
            case LEFT:
                displacementVector = new Vector3(-Configuration.TAILLE_BLOC.get_valeur(), 0,0);
                endPositionMovement.add(displacementVector);
                break;
            case RIGHT:
                displacementVector = new Vector3(Configuration.TAILLE_BLOC.get_valeur(), 0,0);
                endPositionMovement.add(displacementVector);
                break;
            default:
                break;
        }

        return new MovePositionHandler(model3D.transform.getTranslation(new Vector3()),
                endPositionMovement, speedVector);
    }

    public static MovePositionHandler createHandler(Direction direction, Rectangle rectangle) {
        Vector3 speedVector = new Vector3(Configuration.ENVIRONNEMENT_SPEED.get_valeur(), 0,
                Configuration.ENVIRONNEMENT_SPEED.get_valeur());
        //récupération de la position actuelle
        Vector2 hitBoxPosition = rectangle.getPosition(new Vector2());
        Vector3 displacementVector;
        Vector3 endPositionMovement = new Vector3(hitBoxPosition.x, 0 , hitBoxPosition.y);
        switch (direction){
            case UP:
                displacementVector = new Vector3(0,0, -Configuration.TAILLE_BLOC.get_valeur());
                endPositionMovement.add(displacementVector);
                break;
            case DOWN:
                displacementVector = new Vector3(0, 0, Configuration.TAILLE_BLOC.get_valeur());
                endPositionMovement.add(displacementVector);
                break;
            case LEFT:
                displacementVector = new Vector3(-Configuration.TAILLE_BLOC.get_valeur(),0, 0);
                endPositionMovement.add(displacementVector);
                break;
            case RIGHT:
                displacementVector = new Vector3(Configuration.TAILLE_BLOC.get_valeur(),0, 0);
                endPositionMovement.add(displacementVector);
                break;
            default:
                break;
        }

        //transformation en
        Vector2 initialHitbowPosition = rectangle.getPosition(new Vector2());
        Vector3 initial3HitBoxPosition = new Vector3(initialHitbowPosition.x, 0 , initialHitbowPosition.y);
        return new MovePositionHandler(initial3HitBoxPosition,
                endPositionMovement, speedVector);
    }


}
