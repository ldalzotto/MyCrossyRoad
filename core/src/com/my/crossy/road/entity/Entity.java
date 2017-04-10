package com.my.crossy.road.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.GraphicsComponent;
import com.my.crossy.road.entity.component.abs.InputComponent;
import com.my.crossy.road.entity.component.abs.PhysicsComponent;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldalzotto on 12/11/2016.
 */
public class Entity {

    public enum EntityType {
        PLAYER,
        BLOC_OBSTACLE,
        BLOC_DECOR,
        BLOC_OBSTACLE_INVISIBLE;
    }

    protected Vector3 position;
    protected Boolean isMoving = false;
    protected Direction direction = null;
    protected Boolean isDetroyable = false;

    private InputComponent inputComponent;
    private GraphicsComponent graphicsComponent;
    private PhysicsComponent physicsComponent;

    private List<Component> componentList;

    public Entity(InputComponent inputComponent, GraphicsComponent graphicsComponent, PhysicsComponent physicsComponent
                  ){

        componentList = new ArrayList<>();

        this.inputComponent = inputComponent;
        this.graphicsComponent = graphicsComponent;
        this.physicsComponent = physicsComponent;

        if(this.inputComponent != null){
            componentList.add(this.inputComponent);
        }
        if(this.graphicsComponent != null){
            componentList.add(this.graphicsComponent);
        }
        if(this.physicsComponent != null){
            componentList.add(this.physicsComponent);
        }
    }

    public void update(ModelBatch batch, Camera camera, float delta){
        if(inputComponent != null){
            inputComponent.update(this, camera, delta);
        }
        if(graphicsComponent != null){
            graphicsComponent.update(this, batch, camera, delta);
        }
        if(physicsComponent != null){
            physicsComponent.update(this, delta);
        }

    }
    
    public void sendMessage(Component.MESSAGE messageType, String... args){

        StringBuilder fullMessage = new StringBuilder();
        fullMessage.append(messageType.toString());

        for(String string :
                args){
            fullMessage.append(Component.MESSAGE_TOKEN);
            fullMessage.append(string);
        }

        for (Component component :
                componentList) {
            component.receiveMessage(fullMessage.toString());
        }
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    /**
     * Permet de dire que l'entité est en train de se déplacer
     */
    public void isMoving(){
        isMoving = true;
    }

    public void hasMoved(){
        isMoving = false;
    }

    /**
     * Permet de dire si l'entité est en train de se déplacer ou pas
     * @return booleen
     */
    public Boolean getIsMoving(){
        return isMoving;
    }

    public void setIsDetroyable(){
        isDetroyable = true;
    }

    public Boolean getIsDetroyable(){
        return isDetroyable;
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public GraphicsComponent getGraphicsComponent() {
        return graphicsComponent;
    }
}
