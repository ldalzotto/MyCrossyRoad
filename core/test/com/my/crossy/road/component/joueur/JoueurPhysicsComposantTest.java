package com.my.crossy.road.component.joueur;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.entity.component.Component;
import common.enumeration.TypeLigne;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import runner.GdxTestRunner;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 04/04/2017.
 */
@RunWith(GdxTestRunner.class)
public class JoueurPhysicsComposantTest {

    @Test
    public void receiveMessage() throws Exception {
        JoueurPhysicsComposant joueurPhysicsComposant = new JoueurPhysicsComposant();
        Vector3 initialPosition = new Vector3(1f,-1f,1f);


        Json json = new Json();
        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_HITBOX.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(initialPosition, Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);

        joueurPhysicsComposant.receiveMessage(message.toString());
        Assert.assertTrue(joueurPhysicsComposant.getHitbox().x == initialPosition.x);
        Assert.assertTrue(joueurPhysicsComposant.getHitbox().y == initialPosition.z);

    }

    @Test
    public void testWithoutMessage() throws Exception {
        JoueurPhysicsComposant joueurPhysicsComposant = new JoueurPhysicsComposant();
        try {
            joueurPhysicsComposant.receiveMessage(null);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertFalse(true);
            throw e;
        }
    }

    @Test
    public void testWithMessageLengthOfOne() throws Exception {
        JoueurPhysicsComposant joueurPhysicsComposant = new JoueurPhysicsComposant();
        Json json = new Json();
        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_HITBOX.name()).append(Component.MESSAGE_TOKEN);

        joueurPhysicsComposant.receiveMessage(message.toString());

        Assert.assertTrue(joueurPhysicsComposant.getHitbox() == null);

    }

}