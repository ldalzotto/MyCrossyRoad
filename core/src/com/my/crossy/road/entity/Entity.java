package com.my.crossy.road.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.GraphicsComponent;
import com.my.crossy.road.entity.component.abs.InputComponent;
import com.my.crossy.road.entity.component.abs.PhysicsComponent;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by ldalzotto on 12/11/2016.
 */
public class Entity {

    public enum EntityType {
        PLAYER,
        ENVIRONNEMENT,
        BLOC_OBSTACLE,
        BLOC_DECOR;
    }

    protected Vector3 _position;
    protected Boolean _isMoving = false;
    protected Direction _direction = null;
    protected Boolean _isDetroyable = false;

    private InputComponent _inputComponent;
    private GraphicsComponent _graphicsComponent;
    private PhysicsComponent _physicsComponent;

    private List<Component> _componentList;

    public Entity(InputComponent inputComponent, GraphicsComponent graphicsComponent, PhysicsComponent physicsComponent
                  ){

        _componentList = new ArrayList<Component>();

        _inputComponent = inputComponent;
        _graphicsComponent = graphicsComponent;
        _physicsComponent = physicsComponent;

        if(_inputComponent != null){
            _componentList.add(_inputComponent);
        }
        if(_graphicsComponent != null){
            _componentList.add(_graphicsComponent);
        }
        if(_physicsComponent != null){
            _componentList.add(_physicsComponent);
        }
    }

    public void update(ModelBatch batch, Camera camera, Environment environment, float delta){
        if(_inputComponent != null){
            _inputComponent.update(this, camera, delta);
        }
        if(_graphicsComponent != null){
            _graphicsComponent.update(this, batch, camera, environment);
        }
        if(_physicsComponent != null){
            _physicsComponent.update(this);
        }

    }
    
    public void sendMessage(Component.MESSAGE messageType, String... args){

        String fullMessage = messageType.toString();

        for(String string :
                args){
            fullMessage += Component.MESSAGE_TOKEN + string;
        }

        for (Component component :
                _componentList) {
            component.receiveMessage(fullMessage);
        }
    }

    public Vector3 get_position() {
        return _position;
    }

    public void set_position(Vector3 _position) {
        this._position = _position;
    }

    /**
     * Permet de dire que l'entité est en train de se déplacer
     */
    public void isMoving(){
        _isMoving = true;
    }

    public void hasMoved(){
        _isMoving = false;
    }

    /**
     * Permet de dire si l'entité est en train de se déplacer ou pas
     * @return booleen
     */
    public Boolean get_isMoving(){
        return _isMoving;
    }

    public void set_isDetroyable(){
        _isDetroyable = true;
    }

    public Boolean get_isDetroyable(){
        return _isDetroyable;
    }

    public PhysicsComponent get_physicsComponent() {
        return _physicsComponent;
    }

    public Direction get_direction() {
        return _direction;
    }

    public void set_direction(Direction _direction) {
        this._direction = _direction;
    }
}
