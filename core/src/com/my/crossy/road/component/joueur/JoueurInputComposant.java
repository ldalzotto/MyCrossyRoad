package com.my.crossy.road.component.joueur;

import INTEnvironnementManager.INTEnvironnementManager;
import INTEnvironnementManager.exception.CreationLigne;
import INTEnvironnementManager.interfaceManger.IINTEnvironnementManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
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
            }
    }

    @Override
    public void receiveMessage(String message) {

    }

}
