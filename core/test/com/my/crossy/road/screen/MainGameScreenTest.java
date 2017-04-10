package com.my.crossy.road.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.my.crossy.road.component.joueur.JoueurPhysicsComposant;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.configuration.PlayerConfiguration;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.screen.util.player.movement.PlayerMovementManager;
import internal.environnement.manager.INTEnvironnementManager;
import internal.environnement.manager.interfaceclass.manager.IINTEnvironnementManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import runner.GdxTestRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Created by ldalzotto on 27/03/2017.
 */
@RunWith(GdxTestRunner.class)
public class MainGameScreenTest {

    @Before
    public void clearMainGameScreen() throws Exception{
        //clear playermovement manager
        PlayerMovementManager playerMovementManager = PlayerMovementManager.getInstance();
        Field field = playerMovementManager.getClass().getDeclaredField("instance");
        field.setAccessible(true);
        field.set(playerMovementManager, null);
    }

    @After
    public void clearInputMock() throws Exception {
        Gdx.input = Mockito.mock(Input.class);
    }

    @Test
    public void show() throws Exception {
        MainGameScreen mainGameScreen = Mockito.spy(MainGameScreen.class);
        Mockito.doReturn(Mockito.mock(ModelBatch.class)).when(mainGameScreen).createModelBatch();

        mainGameScreen.show();
        Assert.assertTrue(true);

        IINTEnvironnementManager iintEnvironnementManager = INTEnvironnementManager.getInstance();
        int entityCountInitial = iintEnvironnementManager.getEnvironnementLignesPourAffichage().collect(Collectors.toList())
                .stream().map(ligneAffichage -> ligneAffichage.getBlocList().size())
                .reduce((integer, integer2) -> integer+integer2)
                .get();

        //assert the number of entity
        Assert.assertTrue(mainGameScreen.getEntityList().size() == entityCountInitial);

        Assert.assertTrue(mainGameScreen.getJoueur() != null);

    }

    @Test
    public void render_firstMove() throws Exception {
        MainGameScreen mainGameScreen = Mockito.spy(MainGameScreen.class);
        Mockito.doReturn(Mockito.mock(ModelBatch.class)).when(mainGameScreen).createModelBatch();

        mainGameScreen.show();

        //list enetity avant
        List<Entity> entities = new ArrayList<>(mainGameScreen.getEntityList());
        mainGameScreen.render(0.01f);
        Assert.assertEquals(entities, mainGameScreen.getEntityList());

        //simulation of OK up input
        Gdx.input = Mockito.mock(Input.class);
        Mockito.when(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
                .thenReturn(true);
        mainGameScreen.render(8f);

        //pas de création des blocs
        Assert.assertTrue(mainGameScreen.getEntityList().size() == entities.size());

        //blocs are moving
        mainGameScreen.getEntityList().forEach(entity -> {
            if(entity.getPhysicsComponent() != null ){
                Assert.assertTrue(entity.getPhysicsComponent().getMovePositionHandler() != null);
            }
            if(entity.getGraphicsComponent() != null){
                Assert.assertTrue(entity.getGraphicsComponent().getMovePositionHandler() != null);
            }
        });

        Mockito.when(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
                .thenReturn(false);

    }

    @Test
    public void render_allBlocsCreated() throws Exception {
        MainGameScreen mainGameScreen = Mockito.spy(MainGameScreen.class);
        Mockito.doReturn(Mockito.mock(ModelBatch.class)).when(mainGameScreen).createModelBatch();

        mainGameScreen.show();

        //list enetity avant
        List<Entity> entities = new ArrayList<>(mainGameScreen.getEntityList());
        mainGameScreen.render(0.01f);
        Assert.assertEquals(entities, mainGameScreen.getEntityList());

        //simulation of OK up input
        Gdx.input = Mockito.mock(Input.class);
        Mockito.when(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
                .thenReturn(true);
        IntStream.range(0, PlayerConfiguration.MAX_LINES_NB_TO_CREATE_NEW_BLOC.getValue())
                .forEach(value -> {
                    mainGameScreen.render(0.02f);
                    mainGameScreen.render(100f);
                });

        //pas de création des blocs
        Assert.assertTrue(mainGameScreen.getEntityList().size() == entities.size());

        //création des blocs
        mainGameScreen.render(0.02f);
        mainGameScreen.render(100f);
        Assert.assertTrue(mainGameScreen.getEntityList().size() != entities.size());

    }

    @Test
    public void render_doubleTap() throws Exception {
        MainGameScreen mainGameScreen = Mockito.spy(MainGameScreen.class);
        Mockito.doReturn(Mockito.mock(ModelBatch.class)).when(mainGameScreen).createModelBatch();

        mainGameScreen.show();
        mainGameScreen.render(0.001f);

        //simulation of OK up input
        Gdx.input = Mockito.mock(Input.class);
        Mockito.when(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
                .thenReturn(true);

        //récupération de la première position
        Vector3 positionAvant = new Vector3(mainGameScreen.getEntityList().get(0).getPosition());

        IntStream.range(0,3).forEach(value -> {
            //création des blocs
            mainGameScreen.render(0.001f);
        });
        mainGameScreen.render(100f);

        Vector3 positionApres = new Vector3(mainGameScreen.getEntityList().get(0).getPosition());

        Assert.assertTrue(positionApres.dst(positionAvant) == Configuration.TAILLE_BLOC.get_valeur());

    }

    @Test
    public void render_collision() throws Exception {
        MainGameScreen mainGameScreen = Mockito.spy(MainGameScreen.class);
        Mockito.doReturn(Mockito.mock(ModelBatch.class)).when(mainGameScreen).createModelBatch();

        mainGameScreen.show();
        mainGameScreen.render(0.001f);

        //simulation of OK up input
        Gdx.input = Mockito.mock(Input.class);
        Mockito.when(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
                .thenReturn(true);

        //get the position of any entity
        int indexEntity = ThreadLocalRandom.current().nextInt(0, mainGameScreen.getEntityList().size());

        boolean isMoving = true;
        while (isMoving) {
            Vector3 positionBefore = new Vector3(mainGameScreen.getEntityList().get(indexEntity).getPosition());
            mainGameScreen.render(0.02f);
            mainGameScreen.render(100f);
            Vector3 positionAftter = new Vector3(mainGameScreen.getEntityList().get(indexEntity).getPosition());

            if(positionBefore.dst(positionAftter) == 0){
                isMoving = false;
                //a collision has occured
            }
        }

        Assert.assertTrue(true);

    }

}