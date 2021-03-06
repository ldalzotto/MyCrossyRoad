package com.my.crossy.road.component.generic;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.EntityFactory;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.screen.util.MovePositionHandler;
import common.enumeration.TypeLigne;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import runner.GdxTestRunner;

import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 02/04/2017.
 */
@RunWith(GdxTestRunner.class)
public class GenericGraphicComposantTest {

    @Test
    public void receiveMessage_initGraphics() throws Exception {
        GenericGraphicComposant genericGraphicComposant = new GenericGraphicComposant();
        Json json = new Json();

        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_GRAPHICS.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(TypeLigne.ARBRE, TypeLigne.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Vector3(1f,-1f,1f), Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);

        Assert.assertTrue(genericGraphicComposant.getModel3D() == null);

        genericGraphicComposant.receiveMessage(message.toString());

        //récupération de la position du modèle
        Vector3 position = genericGraphicComposant.getModel3D().transform.getTranslation(new Vector3());
        Assert.assertTrue(position.x == 1f);
        Assert.assertTrue(position.y == -1f);
        Assert.assertTrue(position.z == 1f);

        //vérification de la couleur du carré
        ColorAttribute colorAttribute = (ColorAttribute) genericGraphicComposant.getModel3D().materials.get(0).get(ColorAttribute.Diffuse);
        Assert.assertTrue(colorAttribute.color.equals(TypeLigne.ARBRE.getCouleurCube()));
    }

    @Test
    public void receiveMessage_environnementMove() throws Exception {
        GenericGraphicComposant genericGraphicComposant = new GenericGraphicComposant();
        Json json = new Json();

        //envoi du message d'initialisation
        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_GRAPHICS.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(TypeLigne.ARBRE, TypeLigne.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Vector3(1f,-1f,1f), Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);
        genericGraphicComposant.receiveMessage(message.toString());

        //envoi du message du mouvement de l'environnement
        message = new StringBuilder();
        message.append(Component.MESSAGE.ENVIRONNEMENT_MOVE.name()).append(Component.MESSAGE_TOKEN);
        message.append("TEST").append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(Direction.RIGHT, Direction.class)).append(Component.MESSAGE_TOKEN);
        genericGraphicComposant.receiveMessage(message.toString());

        Assert.assertTrue(genericGraphicComposant.getMovePositionHandler() != null);
    }

    @Test
    public void reveiveMesage_environnementMove_withAlreadyMovePositionHandler() throws Exception {
        GenericGraphicComposant genericGraphicComposant = new GenericGraphicComposant();
        Json json = new Json();

        //envoi du message d'initialisation
        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_GRAPHICS.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(TypeLigne.ARBRE, TypeLigne.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Vector3(1f,-1f,1f), Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);
        genericGraphicComposant.receiveMessage(message.toString());

        //envoi du message du mouvement de l'environnement
        message = new StringBuilder();
        message.append(Component.MESSAGE.ENVIRONNEMENT_MOVE.name()).append(Component.MESSAGE_TOKEN);
        message.append("TEST").append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(Direction.RIGHT, Direction.class)).append(Component.MESSAGE_TOKEN);
        genericGraphicComposant.receiveMessage(message.toString());

        MovePositionHandler movePositionHandler = genericGraphicComposant.getMovePositionHandler();

        genericGraphicComposant.receiveMessage(message.toString());
        Assert.assertTrue(genericGraphicComposant.getMovePositionHandler() != null);
        Assert.assertEquals(movePositionHandler, genericGraphicComposant.getMovePositionHandler());
    }

    @Test
    public void sendMessage_withoutMessage() throws Exception {
        GenericGraphicComposant genericGraphicComposant = new GenericGraphicComposant();
        genericGraphicComposant.receiveMessage(null);
        genericGraphicComposant.receiveMessage("");
    }

    @Test
    public void update() throws Exception {
        GenericGraphicComposant genericGraphicComposant = new GenericGraphicComposant();
        ModelBatch modelBatchMock = Mockito.mock(ModelBatch.class);
        Camera camera = Mockito.mock(Camera.class);

        Entity entity = EntityFactory.getEntity(Entity.EntityType.BLOC_DECOR);

        Json json = new Json();
        Vector3 initialPosition = new Vector3(1f,-1f,1f);
        //initialisation des graphiques
        entity.sendMessage(Component.MESSAGE.INIT_GRAPHICS, json.toJson(TypeLigne.ARBRE, TypeLigne.class),
                json.toJson(initialPosition), json.toJson(new Float(1f), Float.class));
        //création du move position handler
        entity.sendMessage(Component.MESSAGE.ENVIRONNEMENT_MOVE, "TEST", json.toJson(Direction.RIGHT, Direction.class));

        entity.update(modelBatchMock, camera, 0.1f);

        Vector3 finalPosition = entity.getPosition();
        //update correct
        Assert.assertTrue(finalPosition.x > initialPosition.x);
        Assert.assertTrue(entity.getIsMoving());
    }

    @Test
    public void updateToTheEndOfEnvironnement_destroyable() throws Exception {
        GenericGraphicComposant genericGraphicComposant = new GenericGraphicComposant();
        ModelBatch modelBatchMock = Mockito.mock(ModelBatch.class);
        Camera camera = Mockito.mock(Camera.class);

        Entity entity = EntityFactory.getEntity(Entity.EntityType.BLOC_DECOR);

        Json json = new Json();
        Vector3 initialPosition = new Vector3(1f,-1f,1f);
        //initialisation des graphiques
        entity.sendMessage(Component.MESSAGE.INIT_GRAPHICS, json.toJson(TypeLigne.ARBRE, TypeLigne.class),
                json.toJson(initialPosition), json.toJson(new Float(1f), Float.class));
        //création du move position handler
        Integer numberOfMove = Math.round(Math.abs(Configuration.POSITION_MIN_ENVIRONNEMENT.get_valeur())/Configuration.TAILLE_BLOC.get_valeur());

        Assert.assertTrue(!entity.getIsDetroyable());

        IntStream.range(0, numberOfMove*2)
                .forEach(value -> {
                    entity.sendMessage(Component.MESSAGE.ENVIRONNEMENT_MOVE, "TEST", json.toJson(Direction.UP, Direction.class));
                    entity.update(modelBatchMock, camera, 20f);
                });

        Assert.assertTrue(entity.getIsDetroyable());
    }

}