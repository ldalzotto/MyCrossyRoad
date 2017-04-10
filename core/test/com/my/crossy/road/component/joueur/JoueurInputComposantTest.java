package com.my.crossy.road.component.joueur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.EntityFactory;

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
public class JoueurInputComposantTest {
    @Test
    public void update_rightKey() throws Exception {
        JoueurInputComposant joueurInputComposant = new JoueurInputComposant();

        Gdx.input = Mockito.mock(Input.class);

        Mockito.when(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
                .thenReturn(true);

        Entity entity = EntityFactory.getEntity(Entity.EntityType.PLAYER);
        Camera camera = Mockito.mock(Camera.class);

        joueurInputComposant.update(entity, camera, 0.1f);

        Assert.assertTrue(entity.getIsMoving());
        Assert.assertTrue(entity.getDirection().equals(Direction.UP));
    }

    @Test
    public void update_leftKey() throws Exception {
        JoueurInputComposant joueurInputComposant = new JoueurInputComposant();

        Gdx.input = Mockito.mock(Input.class);

        Mockito.when(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
                .thenReturn(true);

        Entity entity = EntityFactory.getEntity(Entity.EntityType.PLAYER);
        Camera camera = Mockito.mock(Camera.class);

        joueurInputComposant.update(entity, camera, 0.1f);

        Assert.assertTrue(entity.getIsMoving());
        Assert.assertTrue(entity.getDirection().equals(Direction.DOWN));
    }

    @Test
    public void update_upKey() throws Exception {
        JoueurInputComposant joueurInputComposant = new JoueurInputComposant();

        Gdx.input = Mockito.mock(Input.class);

        Mockito.when(Gdx.input.isKeyJustPressed(Input.Keys.UP))
                .thenReturn(true);

        Entity entity = EntityFactory.getEntity(Entity.EntityType.PLAYER);
        Camera camera = Mockito.mock(Camera.class);

        joueurInputComposant.update(entity, camera, 0.1f);

        Assert.assertTrue(entity.getIsMoving());
        Assert.assertTrue(entity.getDirection().equals(Direction.LEFT));
    }

    @Test
    public void update_downKey() throws Exception {
        JoueurInputComposant joueurInputComposant = new JoueurInputComposant();

        Gdx.input = Mockito.mock(Input.class);

        Mockito.when(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                .thenReturn(true);

        Entity entity = EntityFactory.getEntity(Entity.EntityType.PLAYER);
        Camera camera = Mockito.mock(Camera.class);

        joueurInputComposant.update(entity, camera, 0.1f);

        Assert.assertTrue(entity.getIsMoving());
        Assert.assertTrue(entity.getDirection().equals(Direction.RIGHT));
    }

}