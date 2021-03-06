package com.my.crossy.road.component.joueur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.abs.InputComponent;

/**
 * Created by ldalzotto on 26/02/2017.
 */
public class JoueurInputComposant extends InputComponent {


    @Override
    public void update(Entity entity, Camera camera, float delta) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
                //l'entité est en train de se déplacer
                entity.isMoving();
                entity.setDirection(Direction.UP);
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                entity.isMoving();
                entity.setDirection(Direction.DOWN);
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                entity.isMoving();
                entity.setDirection(Direction.LEFT);
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                entity.isMoving();
                entity.setDirection(Direction.RIGHT);
            }
    }

    @Override
    public void receiveMessage(String message) {
        //no need to update. Only one direction at a time
    }

}
