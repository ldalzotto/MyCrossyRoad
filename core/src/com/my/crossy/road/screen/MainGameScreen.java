package com.my.crossy.road.screen;

import com.my.crossy.road.exception.GameLoadingException;
import com.my.crossy.road.exception.RenderRuntimeExeption;
import internal.environnement.manager.INTEnvironnementManager;
import internal.environnement.manager.exception.EnvironnementNonAffichable;
import internal.environnement.manager.interfaceclass.manager.IINTEnvironnementManager;
import internal.environnement.manager.modele.BlocAffichage;
import internal.environnement.manager.modele.LigneAffichage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.asset.manager.ModelManager;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.EntityFactory;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.screen.util.MainGameScreenUtil;
import com.my.crossy.road.screen.util.player.movement.IPlayerMovementManager;
import com.my.crossy.road.screen.util.player.movement.PlayerMovementManager;
import com.my.crossy.road.screen.viewport.GlobalViewport;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 12/11/2016.
 */
public class MainGameScreen extends GlobalViewport implements Screen{

    private static final String TAG = MainGameScreen.class.getSimpleName();

    private Environment environment;

    private static final int MAIN_GAME_SCREEN_HEIGHT = 800;
    private static final int MAIN_GAME_SCREEN_WIDTH = 600;

    private PerspectiveCamera camera = null;
    private ModelBatch batch = null;

    private ModelManager modelManager = ModelManager.getInstance();
    private IPlayerMovementManager playerMovementManager = PlayerMovementManager.getInstance();

    private Json json;

    private List<Entity> entityList;
    private Entity joueur;

    /**
     * L'initialisation de l'environnement est déjà effectuée dans le constructeur
     */
    private IINTEnvironnementManager iintEnvironnementManager = INTEnvironnementManager.getInstance();

    public MainGameScreen(){

        try {
            setupViewport(MAIN_GAME_SCREEN_WIDTH, MAIN_GAME_SCREEN_HEIGHT);

            json = new Json();

            environment = new Environment();
            environment.set(new ColorAttribute(ColorAttribute.AmbientLight, Color.BLACK));
            environment.add(new DirectionalLight().set(Color.WHITE, -1f, -0.8f, -0.2f));
            environment.add(new DirectionalLight().set(Color.WHITE, 1f, 0.8f, 0.2f));

            entityList = new ArrayList<>();

            loadingAssets();

            populateEntity();
        } catch (EnvironnementNonAffichable environnementNonAffichable) {
            Gdx.app.debug(TAG, environnementNonAffichable.getMessage(), environnementNonAffichable);
            throw new GameLoadingException("The game cannot be loaded.", environnementNonAffichable);
        }
    }

    @Override
    public void show() {
        batch = createModelBatch();

        camera = new PerspectiveCamera(67, VIEWPORT.getViewportWidth(), VIEWPORT.getViewportHeight());
        camera.position.set(-20f, 70f, -20f);
        camera.lookAt(5,0,5);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();

        Gdx.input.setInputProcessor(new CameraInputController(camera));
    }

    @Override
    public void render(float delta) {
        camera.update();

        Gdx.gl.glViewport(0, 0, (int)VIEWPORT.getViewportWidth(), (int)VIEWPORT.getViewportHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        entityList.forEach(entity -> entity.update(batch, camera, delta));
        joueur.update(batch, camera, delta);

        //on vérifie si le joueur a effectué un mouvement
        if(joueur.getIsMoving()){
            Entity firstEntityMoving = entityList.stream().findFirst().orElse(null);
            //si les composants de l'environnement ne bougent pas
            if(firstEntityMoving!=null && !firstEntityMoving.getIsMoving()) {

                List<Entity> entityToCompare = new ArrayList<>(entityList);

                /**
                 * On supprime les blocs de l'environnement contenu dans le joueur puis on fait avancer l'environnement
                 * fictif d'une demi position. Ensuite, on vérifie si il y a une collision entre le joueur
                 * et l'environnement fictif
                 */
                Boolean isCollision = MainGameScreenUtil
                                .chekCollisionWithFutureMove(entityToCompare, json, joueur);
                if(!isCollision) {
                    //si le déplacement est accepté (pas de collision)
                    executeMove();
                } else {
                    Gdx.app.debug(TAG, "Collision has been detected !");
                }
                entityToCompare.clear();
                joueur.hasMoved();

            }
        }

        //Entity garbage collector
        List<Entity> entitiesToDestroy = entityList.stream().filter(Entity::getIsDetroyable).collect(Collectors.toList());
        entitiesToDestroy.forEach(entityList::remove);
    }

    /**
     * Permet d'exécuter un mouvement du jouer. L'exécution d'un mouvement se traduit par le déplacement
     * complet de l'environnement dans la position inverse au déplacement du joueur
     */
    private void executeMove() {
        //le déplacement est accepté
        //mise à jour de l'index width du joueur
        playerMovementManager.updatePlayerWidthIndex(joueur.getDirection());
        entityList.forEach(entity1 ->
                entity1.sendMessage(Component.MESSAGE.ENVIRONNEMENT_MOVE, json.toJson(Configuration.POSITION_MIN_ENVIRONNEMENT.get_valeur()),
                        json.toJson(joueur.getDirection())));
        List<Entity> entityToCreate = createNewBlocsIfAvailable();
        entityList.addAll(entityToCreate);
    }

    private List<Entity> createNewBlocsIfAvailable() {
        List<Entity> entities = new ArrayList<>();
            if(joueur.getDirection().equals(Direction.UP) && playerMovementManager.isAbleToCreateNewBlocs(entityList, joueur)){
                entities = createBlocsToLastPosition(Configuration.TAILLE_BLOC.get_valeur());
            }
            return entities;
    }

    private void populateEntity() throws EnvironnementNonAffichable{

        //création de tous les blocs
        List<Entity> blocs = createBlocs(Configuration.TAILLE_BLOC.get_valeur());
        entityList.addAll(blocs);

            //initialisation joueur
            joueur = EntityFactory.getEntity(Entity.EntityType.PLAYER);
            Integer positionJoueur = iintEnvironnementManager.getSpawnJoueurPosition();
            joueur.sendMessage(Component.MESSAGE.INIT_GRAPHICS, null, json.toJson(new Vector3(positionJoueur*Configuration.TAILLE_JOUEUR.get_valeur(),
                    Configuration.TAILLE_JOUEUR.get_valeur(),0)), json.toJson(Configuration.TAILLE_JOUEUR.get_valeur()));
            joueur.sendMessage(Component.MESSAGE.INIT_HITBOX, json.toJson(new Vector3(positionJoueur*Configuration.TAILLE_JOUEUR.get_valeur(),
                    Configuration.TAILLE_JOUEUR.get_valeur(),0)), json.toJson(Configuration.TAILLE_JOUEUR.get_valeur()));
    }

    /**
     * Permet de créer toutes les entités de blocs de l'environnement actuel {@link IINTEnvironnementManager}.
     * @param size la taille des blocs générés
     * @return la liste de tous les blocs créé
     */
    private List<Entity> createBlocs(Float size){
        List<Entity> entities = new ArrayList<>();
            List<LigneAffichage> ligneAffichages = iintEnvironnementManager.getEnvironnementLignesPourAffichage().collect(Collectors.toList());
            IntStream.range(0, ligneAffichages.size())
                    .mapToObj(MainGameScreenUtil.formatTypeLigneAffichage(ligneAffichages))
                    .map(MainGameScreenUtil.determinerPosition(Configuration.TAILLE_BLOC.get_valeur()))
                    .forEach(ligneIndexToLigneAffichageToTypeLigneAffichageToPosition ->
                        ligneIndexToLigneAffichageToTypeLigneAffichageToPosition.forEach((integer, ligneAffichageMapMap) ->
                            ligneAffichageMapMap.forEach((ligneAffichage, typeLigneAffichageListMap) ->
                                typeLigneAffichageListMap.forEach((typeLigneAffichage, blocAndPosition) ->
                                    blocAndPosition.forEach(blocAffichageVector3Map ->
                                        blocAffichageVector3Map
                                                .forEach(MainGameScreenUtil.createGameBlocEntity(size, entities, typeLigneAffichage, json))
                                    )
                                )
                            )
                        )
                    );
        return entities;
    }

    /**
     * Permet d'initialiser et de renvoyer les entités de blocs de la dernière ligne créé en renseignant la taille des blocs.
     * souhaitée
     * @param size la taille des blocs créé
     * @return la liste des entités créées
     */
    private List<Entity> createBlocsToLastPosition(Float size){
        List<Entity> entities = new ArrayList<>();
        try {
            Float position = MainGameScreenUtil.getMaxBlocPosition(entityList);
            LigneAffichage ligneAffichageCree = iintEnvironnementManager.creationLigne();
            IntStream.range(0, ligneAffichageCree.getBlocList().size())
                    .forEach(value -> {
                        BlocAffichage blocToCreate = ligneAffichageCree.getBlocList().get(value);
                        Entity entity = MainGameScreenUtil.generateEntity(blocToCreate);

                        float widthPlayerPosition = (value+ playerMovementManager.getPlayerWidthIndex()) * Configuration.TAILLE_BLOC.get_valeur();
                        Vector3 entityPosition = new Vector3(widthPlayerPosition, 0, position);

                        entity.sendMessage(Component.MESSAGE.INIT_GRAPHICS, json.toJson(ligneAffichageCree.getTypeLigne()),
                                json.toJson(entityPosition), json.toJson(size));
                        entity.sendMessage(Component.MESSAGE.INIT_HITBOX, json.toJson(entityPosition), json.toJson(size));
                        entities.add(entity);
                    });
            return entities;
        } catch (Exception e) {
            Gdx.app.debug(TAG, "An error occured while creating blocs to last position.", e);
            throw new RenderRuntimeExeption("An error occured while rendering.", e);
        }
    }

    private void loadingAssets(){
        modelManager.loadModels(Arrays.asList("D:\\Profiles\\ldalzotto\\IdeaProjects\\MyCrossyRoad\\core\\assets\\ship.obj",
                "D:\\Profiles\\ldalzotto\\IdeaProjects\\MyCrossyRoad\\core\\assets\\block.obj",
                "D:\\Profiles\\ldalzotto\\IdeaProjects\\MyCrossyRoad\\core\\assets\\invader.obj"
        ));
    }

    public ModelBatch createModelBatch(){
        return new ModelBatch();
    }

    @Override
    public void resize(int width, int height) {
        //not implemented
    }

    @Override
    public void pause() {
        //not implemented
    }

    @Override
    public void resume() {
        //not implemented
    }

    @Override
    public void hide() {
        //not implemented
    }

    @Override
    public void dispose() {
        //not implemented
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public Entity getJoueur() {
        return joueur;
    }
}
