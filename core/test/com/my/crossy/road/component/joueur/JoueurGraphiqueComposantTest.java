package com.my.crossy.road.component.joueur;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.component.generic.GenericGraphicComposant;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.EntityFactory;
import com.my.crossy.road.entity.component.Component;
import common.enumeration.TypeLigne;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import runner.GdxTestRunner;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 05/04/2017.
 */
@RunWith(GdxTestRunner.class)
public class JoueurGraphiqueComposantTest {

    @Test
    public void receiveMessage_initGraphics() throws Exception {
        JoueurGraphiqueComposant joueurGraphiqueComposant = new JoueurGraphiqueComposant();
        Json json = new Json();

        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_GRAPHICS.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(TypeLigne.ARBRE, TypeLigne.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Vector3(1f,-1f,1f), Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);

        Assert.assertTrue(joueurGraphiqueComposant.getModel3D() == null);

        joueurGraphiqueComposant.receiveMessage(message.toString());

        //récupération de la position du modèle
        Vector3 position = joueurGraphiqueComposant.getModel3D().transform.getTranslation(new Vector3());
        Assert.assertTrue(position.x == 1f);
        Assert.assertTrue(position.y == -1f);
        Assert.assertTrue(position.z == 1f);

        //vérification de la couleur du carré
        ColorAttribute colorAttribute = (ColorAttribute) joueurGraphiqueComposant.getModel3D().materials.get(0).get(ColorAttribute.Diffuse);
        Assert.assertTrue(colorAttribute.color.equals(Color.PINK));
    }

    @Test
    public void receiveMessage_playerMoveForward() throws Exception {
        JoueurGraphiqueComposant joueurGraphiqueComposant = new JoueurGraphiqueComposant();
        Json json = new Json();

        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_GRAPHICS.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(TypeLigne.ARBRE, TypeLigne.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Vector3(1f,-1f,1f), Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);
        joueurGraphiqueComposant.receiveMessage(message.toString());

        Vector3 initialPosition = joueurGraphiqueComposant.getModel3D().transform.getTranslation(new Vector3());

        message = new StringBuilder();
        message.append(Component.MESSAGE.PLAYER_MOVE_FORWARD.name()).append(Component.MESSAGE_TOKEN);
        message.append("NULL").append(Component.MESSAGE_TOKEN);
        joueurGraphiqueComposant.receiveMessage(message.toString());

        Vector3 finalPosition = joueurGraphiqueComposant.getModel3D().transform.getTranslation(new Vector3());

        Assert.assertTrue(initialPosition.x == finalPosition.x);
        Assert.assertTrue(initialPosition.y == finalPosition.y);
        Assert.assertTrue(Math.abs(finalPosition.z - initialPosition.z) == Configuration.TAILLE_BLOC.get_valeur());

    }

    @Test
    public void receiveMessage_noMessage() throws Exception {
        JoueurGraphiqueComposant joueurGraphiqueComposant = new JoueurGraphiqueComposant();
        joueurGraphiqueComposant.receiveMessage(null);

        Assert.assertTrue(true);

        joueurGraphiqueComposant.receiveMessage("");
        Assert.assertTrue(joueurGraphiqueComposant.getModel3D() == null);
    }

    @Test
    public void update() throws Exception {
        JoueurGraphiqueComposant joueurGraphiqueComposant = new JoueurGraphiqueComposant();
        Json json = new Json();

        StringBuilder message = new StringBuilder();
        message.append(Component.MESSAGE.INIT_GRAPHICS.name()).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(TypeLigne.ARBRE, TypeLigne.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Vector3(1f,-1f,1f), Vector3.class)).append(Component.MESSAGE_TOKEN);
        message.append(json.toJson(new Float(1f), Float.class)).append(Component.MESSAGE_TOKEN);
        joueurGraphiqueComposant.receiveMessage(message.toString());

        Vector3 initialPosition = joueurGraphiqueComposant.getModel3D().transform.getTranslation(new Vector3());

        Entity entity = EntityFactory.getEntity(Entity.EntityType.PLAYER);
        ModelBatch modelBatch = Mockito.mock(ModelBatch.class);
        Camera camera = Mockito.mock(Camera.class);

        joueurGraphiqueComposant.update(entity, modelBatch, camera, 1000f);

        Assert.assertTrue(initialPosition.equals(entity.getPosition()));

    }

}