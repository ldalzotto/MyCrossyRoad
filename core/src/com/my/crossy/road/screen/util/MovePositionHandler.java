package com.my.crossy.road.screen.util;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by ldalzotto on 13/03/2017.
 */
public class MovePositionHandler {

    private Vector3 initialPosition;
    private Vector3 finalPosition;
    private Vector3 speed;

    private Vector3 currentPosition;

    private Float lastDistance;

    private Vector3 directionVector;

    private Boolean isRuning;

    public MovePositionHandler(Vector3 initialPosition, Vector3 finalPosition, Vector3 speed){
        this.initialPosition = initialPosition.cpy();
        currentPosition = initialPosition.cpy();
        this.finalPosition = finalPosition;
        this.speed = speed;

        directionVector = new Vector3(this.finalPosition.x - this.initialPosition.x,
                this.finalPosition.y - this.initialPosition.y,
                this.finalPosition.z - this.initialPosition.z);
        directionVector.nor();

        lastDistance = currentPosition.dst(this.finalPosition);
        isRuning = true;
    }

    public Vector3 updatePosition(Float delta){
        if(isRuning){

            Vector3 localSpeed = speed.cpy();
            Vector3 localDirectionVector = directionVector.cpy();

            Float currentDistance = currentPosition.dst(finalPosition);
            lastDistance = currentDistance;

                localSpeed.scl(delta);
                Vector3 distanceParcoured = new Vector3();

                distanceParcoured.x = localDirectionVector.x * localSpeed.x;
                distanceParcoured.y = localDirectionVector.y * localSpeed.y;
                distanceParcoured.z = localDirectionVector.z * localSpeed.z;

                currentPosition = currentPosition.add(distanceParcoured);

            //clamp distance at the end
            currentDistance = currentPosition.dst(finalPosition);
            if(currentDistance > lastDistance){
                currentPosition = finalPosition;
                isRuning = false;
            }
        }

        return currentPosition;
    }

    public MovePositionHandler reEvaluate(){
        if(currentPosition.equals(finalPosition)){
            return null;
        }else {
            return this;
        }
    }


}
