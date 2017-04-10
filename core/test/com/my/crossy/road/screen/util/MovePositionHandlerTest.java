package com.my.crossy.road.screen.util;

import com.badlogic.gdx.math.Vector3;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 05/04/2017.
 */
public class MovePositionHandlerTest {
    @Test
    public void reEvaluate_withClampedPosition() throws Exception {
        Vector3 initialPosition = new Vector3(0f,1f,2f);
        Vector3 finalPosition = new Vector3(1f,2f,3f);
        Vector3 speed = new Vector3(0.1f, 0.1f, 0.1f);
        MovePositionHandler movePositionHandler = new MovePositionHandler(initialPosition, finalPosition, speed);

        Assert.assertTrue(movePositionHandler.reEvaluate().equals(movePositionHandler));

        movePositionHandler.updatePosition(10000f);

        Assert.assertTrue(movePositionHandler.reEvaluate() == null);
    }

    @Test
    public void updatePosition_onEndingMovement() throws Exception {
        Vector3 initialPosition = new Vector3(0f,1f,2f);
        Vector3 finalPosition = new Vector3(1f,2f,3f);
        Vector3 speed = new Vector3(0.1f, 0.1f, 0.1f);
        MovePositionHandler movePositionHandler = new MovePositionHandler(initialPosition, finalPosition, speed);

        Assert.assertTrue(movePositionHandler.reEvaluate().equals(movePositionHandler));

        movePositionHandler.updatePosition(10000f);
        Assert.assertTrue(movePositionHandler.reEvaluate() == null);

        movePositionHandler.updatePosition(10000f);
        Assert.assertTrue(movePositionHandler.reEvaluate() == null);
    }

}