package com.my.crossy.road.component.generic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.EntityFactory;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.PhysicsComponent;
import com.my.crossy.road.screen.util.MovePositionHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import runner.GdxTestRunner;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 03/04/2017.
 */
@RunWith(GdxTestRunner.class)
public class GenericPhysicComposantTest {
    @Test
    public void receiveMessage_initHitbox() throws Exception {
        GenericPhysicComposant genericPhysicComposant = new GenericPhysicComposant();
        Vector3 initialPosition = new Vector3(1f, 1f, 1f);

        Json json = new Json();
        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_HITBOX.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(initialPosition, Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);

        genericPhysicComposant.receiveMessage(message.toString());

        Assert.assertTrue(genericPhysicComposant.getHitbox().x == initialPosition.x);
        Assert.assertTrue(genericPhysicComposant.getHitbox().y == initialPosition.z);
    }

    @Test
    public void receiveMessage_empty() throws Exception {
        GenericPhysicComposant genericPhysicComposant = new GenericPhysicComposant();
        Vector3 initialPosition = new Vector3(1f, 1f, 1f);

        genericPhysicComposant.receiveMessage(null);

        Assert.assertTrue(genericPhysicComposant.getMovePositionHandler() == null);
    }

    @Test
    public void receiveMessage_environnementMove() throws Exception {
        GenericPhysicComposant genericPhysicComposant = new GenericPhysicComposant();
        Vector3 initialPosition = new Vector3(1f, 1f, 1f);

        //initialisation
        Json json = new Json();
        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_HITBOX.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(initialPosition, Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);

        genericPhysicComposant.receiveMessage(message.toString());

        Assert.assertTrue(genericPhysicComposant.getMovePositionHandler() == null);

        //déplacement
        message = new StringBuilder();
        message.append(Component.MESSAGE.ENVIRONNEMENT_MOVE.name()).append(Component.MESSAGE_TOKEN);
        message.append("TEST").append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(Direction.RIGHT)).append(Component.MESSAGE_TOKEN);
        genericPhysicComposant.receiveMessage(message.toString());

        MovePositionHandler movePositionHandler = genericPhysicComposant.getMovePositionHandler();
        Assert.assertTrue(movePositionHandler != null);

        genericPhysicComposant.receiveMessage(message.toString());
        Assert.assertEquals(movePositionHandler, genericPhysicComposant.getMovePositionHandler());

    }

    @Test
    public void receiveMessage_environnementFutureMove() throws Exception {
        GenericPhysicComposant genericPhysicComposant = new GenericPhysicComposant();
        Vector3 initialPosition = new Vector3(1f, 1f, 1f);

        Json json = new Json();
        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_HITBOX.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(initialPosition, Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);

        genericPhysicComposant.receiveMessage(message.toString());

        Vector2 beginPositionHitbox = genericPhysicComposant.getHitbox().getPosition(new Vector2());

        //déplacement
        message = new StringBuilder();
        message.append(Component.MESSAGE.ENVIRONNEMENT_FUTURE_MOVE.name()).append(Component.MESSAGE_TOKEN);
        message.append("TEST").append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(Direction.UP)).append(Component.MESSAGE_TOKEN);
        genericPhysicComposant.receiveMessage(message.toString());

        Vector2 finalPositionHitbox = genericPhysicComposant.getHitbox().getPosition(new Vector2());
        Assert.assertTrue((finalPositionHitbox.y - beginPositionHitbox.y) == -Configuration.TAILLE_BLOC.get_valeur()/2);

    }

    @Test
    public void receiveMessage_environnementFutureMove_Left() throws Exception {
        GenericPhysicComposant genericPhysicComposant = new GenericPhysicComposant();
        Vector3 initialPosition = new Vector3(1f, 1f, 1f);

        Json json = new Json();
        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_HITBOX.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(initialPosition, Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);

        genericPhysicComposant.receiveMessage(message.toString());

        Vector2 beginPositionHitbox = genericPhysicComposant.getHitbox().getPosition(new Vector2());

        //déplacement
        message = new StringBuilder();
        message.append(Component.MESSAGE.ENVIRONNEMENT_FUTURE_MOVE.name()).append(Component.MESSAGE_TOKEN);
        message.append("TEST").append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(Direction.LEFT)).append(Component.MESSAGE_TOKEN);
        genericPhysicComposant.receiveMessage(message.toString());

        Vector2 finalPositionHitbox = genericPhysicComposant.getHitbox().getPosition(new Vector2());
        Assert.assertTrue((finalPositionHitbox.x - beginPositionHitbox.x) == -Configuration.TAILLE_BLOC.get_valeur()/2);

    }

    @Test
    public void receiveMessage_environnementFutureMove_Right() throws Exception {
        GenericPhysicComposant genericPhysicComposant = new GenericPhysicComposant();
        Vector3 initialPosition = new Vector3(1f, 1f, 1f);

        Json json = new Json();
        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_HITBOX.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(initialPosition, Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);

        genericPhysicComposant.receiveMessage(message.toString());

        Vector2 beginPositionHitbox = genericPhysicComposant.getHitbox().getPosition(new Vector2());

        //déplacement
        message = new StringBuilder();
        message.append(Component.MESSAGE.ENVIRONNEMENT_FUTURE_MOVE.name()).append(Component.MESSAGE_TOKEN);
        message.append("TEST").append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(Direction.RIGHT)).append(Component.MESSAGE_TOKEN);
        genericPhysicComposant.receiveMessage(message.toString());

        Vector2 finalPositionHitbox = genericPhysicComposant.getHitbox().getPosition(new Vector2());
        Assert.assertTrue((finalPositionHitbox.x - beginPositionHitbox.x) == Configuration.TAILLE_BLOC.get_valeur()/2);

    }

    @Test
    public void receiveMessage_environnementFutureMove_Down() throws Exception {
        GenericPhysicComposant genericPhysicComposant = new GenericPhysicComposant();
        Vector3 initialPosition = new Vector3(1f, 1f, 1f);

        Json json = new Json();
        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_HITBOX.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(initialPosition, Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);

        genericPhysicComposant.receiveMessage(message.toString());

        Vector2 beginPositionHitbox = genericPhysicComposant.getHitbox().getPosition(new Vector2());

        //déplacement
        message = new StringBuilder();
        message.append(Component.MESSAGE.ENVIRONNEMENT_FUTURE_MOVE.name()).append(Component.MESSAGE_TOKEN);
        message.append("TEST").append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(Direction.DOWN)).append(Component.MESSAGE_TOKEN);
        genericPhysicComposant.receiveMessage(message.toString());

        Vector2 finalPositionHitbox = genericPhysicComposant.getHitbox().getPosition(new Vector2());
        Assert.assertTrue((finalPositionHitbox.y - beginPositionHitbox.y) == Configuration.TAILLE_BLOC.get_valeur()/2);

    }

    @Test
    public void update_movePositionHandlerCreated() throws Exception {
        Entity entity = EntityFactory.getEntity(Entity.EntityType.BLOC_OBSTACLE);
        PhysicsComponent genericPhysicComposant = entity.getPhysicsComponent();
        Vector3 initialPosition = new Vector3(1f, 1f, 1f);

        Json json = new Json();
        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_HITBOX.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(initialPosition, Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);

        genericPhysicComposant.receiveMessage(message.toString());

        Vector2 beginPositionHitbox = genericPhysicComposant.getHitbox().getPosition(new Vector2());

        //déplacement
        message = new StringBuilder();
        message.append(Component.MESSAGE.ENVIRONNEMENT_MOVE.name()).append(Component.MESSAGE_TOKEN);
        message.append("TEST").append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(Direction.DOWN)).append(Component.MESSAGE_TOKEN);
        genericPhysicComposant.receiveMessage(message.toString());

        genericPhysicComposant.update(entity, 0.0f);

        Assert.assertTrue(entity.getIsMoving());
        Assert.assertTrue(genericPhysicComposant != null);

        Integer numberOfMove = Math.round(Math.abs(Configuration.POSITION_MIN_ENVIRONNEMENT.get_valeur())/Configuration.TAILLE_BLOC.get_valeur());

        Assert.assertTrue(!entity.getIsDetroyable());



        final StringBuilder messageEnvironnementMove = new StringBuilder();
        messageEnvironnementMove.append(Component.MESSAGE.ENVIRONNEMENT_MOVE.name()).append(Component.MESSAGE_TOKEN);
        messageEnvironnementMove.append("TEST").append(Component.MESSAGE_TOKEN);
        messageEnvironnementMove.append(json.toJson(Direction.UP)).append(Component.MESSAGE_TOKEN);
        IntStream.range(0, numberOfMove*2)
                .forEach(value -> {
                    genericPhysicComposant.receiveMessage(messageEnvironnementMove.toString());
                    genericPhysicComposant.update(entity, 100000f);
                });

        Assert.assertTrue(entity.getIsDetroyable());



    }

}