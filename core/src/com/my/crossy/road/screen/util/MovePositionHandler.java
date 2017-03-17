package com.my.crossy.road.screen.util;

import com.badlogic.gdx.math.Vector3;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ldalzotto on 13/03/2017.
 */
public class MovePositionHandler {

    private Vector3 _initialPosition;
    private Vector3 _finalPosition;
    private Vector3 _speed;

    private List<Boolean> _diffPositionIncrease = Arrays.asList(false, false, false);

    private Vector3 _currentPosition;

    private Float _maxDistance;
    private Float _lastDistance;

    private Vector3 _directionVector;

    private Boolean _isRuning;

    public MovePositionHandler(Vector3 initialPosition, Vector3 finalPosition, Vector3 speed){
        _initialPosition = initialPosition.cpy();
        _currentPosition = initialPosition.cpy();
        _finalPosition = finalPosition;
        _speed = speed;

        _directionVector = new Vector3(_finalPosition.x - _initialPosition.x,
                _finalPosition.y - _initialPosition.y,
                _finalPosition.z - _initialPosition.z);
        _directionVector.nor();

        _maxDistance = _initialPosition.dst(_finalPosition);


        if(_finalPosition.x >= _initialPosition.x){
            _diffPositionIncrease.set(0, true);
        }
        if(_finalPosition.y >= _initialPosition.y){
            _diffPositionIncrease.set(1, true);
        }
        if(_finalPosition.z >= _initialPosition.z){
            _diffPositionIncrease.set(2, true);
        }

        _lastDistance = _currentPosition.dst(_finalPosition);
        _isRuning = true;
    }

    public Vector3 updatePosition(Float delta){
        if(_isRuning){

            Boolean isMoveAllowed = true;

            Vector3 localSpeed = _speed.cpy();
            Vector3 localDirectionVector = _directionVector.cpy();

            Float currentDistance = _currentPosition.dst(_finalPosition);
            if(currentDistance > _lastDistance){
                isMoveAllowed = false;
            } else {
                _lastDistance = currentDistance;
            }

            if(!isMoveAllowed){
                _currentPosition = _finalPosition;
                _isRuning = false;
            } else {
                localSpeed.scl(delta);
                Vector3 distanceParcoured = new Vector3();

                distanceParcoured.x = localDirectionVector.x * localSpeed.x;
                distanceParcoured.y = localDirectionVector.y * localSpeed.y;
                distanceParcoured.z = localDirectionVector.z * localSpeed.z;

                _currentPosition = _currentPosition.add(distanceParcoured);
            }

        }

        return _currentPosition;
    }

    public MovePositionHandler reEvaluate(){
        if(_currentPosition.equals(_finalPosition)){
            return null;
        }else {
            return this;
        }
    }


}
