package com.my.crossy.road.screen;

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
import com.my.crossy.road.assetManager.ModelManager;
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

    private Environment _environment;

    private static final int MAIN_GAME_SCREEN_HEIGHT = 800;
    private static final int MAIN_GAME_SCREEN_WIDTH = 600;

    private PerspectiveCamera _camera = null;
    private ModelBatch _batch = null;

    private ModelManager _modelManager = ModelManager.getInstance();
    private IPlayerMovementManager _playerMovementManager = PlayerMovementManager.get_instance();

    private Json _json;

    private List<Entity> _entityList;
    private Entity _joueur;

    /**
     * L'initialisation de l'environnement est déjà effectuée dans le constructeur
     */
    private IINTEnvironnementManager _intEnvironnementManager = INTEnvironnementManager.getInstance();

    public MainGameScreen(){

        setupViewport(MAIN_GAME_SCREEN_WIDTH, MAIN_GAME_SCREEN_HEIGHT);

        _json = new Json();

        _environment = new Environment();
        _environment.set(new ColorAttribute(ColorAttribute.AmbientLight, Color.BLACK));
        _environment.add(new DirectionalLight().set(Color.WHITE, -1f, -0.8f, -0.2f));
        _environment.add(new DirectionalLight().set(Color.WHITE, 1f, 0.8f, 0.2f));

        _entityList = new ArrayList<>();

        loadingAssets();

        populateEntity();
    }

    @Override
    public void show() {
        _batch = createModelBatch();

        _camera = new PerspectiveCamera(67, VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);
        _camera.position.set(-20f, 70f, -20f);
        _camera.lookAt(5,0,5);
        _camera.near = 1f;
        _camera.far = 300f;
        _camera.update();

        Gdx.input.setInputProcessor(new CameraInputController(_camera));
    }

    @Override
    public void render(float delta) {
        _camera.update();

        Gdx.gl.glViewport(0, 0, (int)VIEWPORT.viewportWidth, (int)VIEWPORT.viewportHeight);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        _entityList.forEach(entity -> entity.update(_batch, _camera, delta));
        _joueur.update(_batch, _camera, delta);

        //on vérifie si le joueur a effectué un mouvement
        if(_joueur.getIsMoving()){

            //si les composants de l'environnement ne bougent pas
            if(!_entityList.stream().findFirst().get().getIsMoving()) {

                List<Entity> entityToCompare = _entityList.stream().collect(Collectors.toList());

                /**
                 * On supprime les blocs de l'environnement contenu dans le joueur puis on fait avancer l'environnement
                 * fictif d'une demi position. Ensuite, on vérifie si il y a une collision entre le joueur
                 * et l'environnement fictif
                 */
                entityToCompare.forEach(entity1 ->
                        entity1.sendMessage(Component.MESSAGE.ENVIRONNEMENT_FUTURE_MOVE, _json.toJson(Configuration.POSITION_MIN_ENVIRONNEMENT.get_valeur()),
                                _json.toJson(_joueur.getDirection())));
                Boolean isCollision = MainGameScreenUtil.checkCollision(_joueur, entityToCompare);
                entityToCompare.forEach(entity1 ->
                        entity1.sendMessage(Component.MESSAGE.ENVIRONNEMENT_FUTURE_MOVE, _json.toJson(Configuration.POSITION_MIN_ENVIRONNEMENT.get_valeur()),
                                _json.toJson(Direction.getOpposite(_joueur.getDirection()))));
                if(!isCollision) {
                    //si le déplacement est accepté (pas de collision)
                    executeMove();
                } else {
                    Gdx.app.debug(TAG, "Collision has been detected !");
                }
                entityToCompare.clear();
                _joueur.hasMoved();

            }
        }

        //Entity garbage collector
        List<Entity> entitiesToDestroy = _entityList.stream().filter(Entity::getIsDetroyable).collect(Collectors.toList());
        entitiesToDestroy.forEach(_entityList::remove);
    }

    /**
     * Permet d'exécuter un mouvement du jouer. L'exécution d'un mouvement se traduit par le déplacement
     * complet de l'environnement dans la position inverse au déplacement du joueur
     */
    private void executeMove() {
        //le déplacement est accepté
        //mise à jour de l'index width du joueur
        _playerMovementManager.updatePlayerWidthIndex(_joueur.getDirection());
        _entityList.forEach(entity1 ->
                entity1.sendMessage(Component.MESSAGE.ENVIRONNEMENT_MOVE, _json.toJson(Configuration.POSITION_MIN_ENVIRONNEMENT.get_valeur()),
                        _json.toJson(_joueur.getDirection())));
        List<Entity> entityToCreate = createNewBlocsIfAvailable();
        _entityList.addAll(entityToCreate);
    }

    private List<Entity> createNewBlocsIfAvailable() {
        List<Entity> entities = new ArrayList<>();
        try {
            if(_joueur.getDirection().equals(Direction.UP) && _playerMovementManager.isAbleToCreateNewBlocs(_entityList, _joueur)){
                entities = createBlocsToLastPosition(Configuration.TAILLE_BLOC.get_valeur());
            }
            return entities;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }

    private void populateEntity(){

        //création de tous les blocs
        List<Entity> blocs = createBlocs(Configuration.TAILLE_BLOC.get_valeur());
        _entityList.addAll(blocs);

        try {
            //initialisation joueur
            Entity joueur = EntityFactory.getEntity(Entity.EntityType.PLAYER);
            Integer positionJoueur = _intEnvironnementManager.getSpawnJoueurPosition();
            joueur.sendMessage(Component.MESSAGE.INIT_GRAPHICS, null, _json.toJson(new Vector3(positionJoueur*Configuration.TAILLE_JOUEUR.get_valeur(),
                    Configuration.TAILLE_JOUEUR.get_valeur(),0)), _json.toJson(Configuration.TAILLE_JOUEUR.get_valeur()));
            joueur.sendMessage(Component.MESSAGE.INIT_HITBOX, _json.toJson(new Vector3(positionJoueur*Configuration.TAILLE_JOUEUR.get_valeur(),
                    Configuration.TAILLE_JOUEUR.get_valeur(),0)), _json.toJson(Configuration.TAILLE_JOUEUR.get_valeur()));
            _joueur = joueur;
        } catch (EnvironnementNonAffichable environnementNonAffichable) {
            environnementNonAffichable.printStackTrace();
        }
    }

    /**
     * Permet de créer toutes les entités de blocs de l'environnement actuel {@link IINTEnvironnementManager}.
     * @param size la taille des blocs générés
     * @return la liste de tous les blocs créé
     */
    private List<Entity> createBlocs(Float size){
        List<Entity> entities = new ArrayList<>();
        try {
            List<LigneAffichage> ligneAffichages = _intEnvironnementManager.getEnvironnementLignesPourAffichage().collect(Collectors.toList());
            IntStream.range(0, ligneAffichages.size())
                    .mapToObj(MainGameScreenUtil.formatTypeLigneAffichage(ligneAffichages))
                    .map(MainGameScreenUtil.determinerPosition(Configuration.TAILLE_BLOC.get_valeur()))
                    .forEach(ligneIndexToLigneAffichageToTypeLigneAffichageToPosition -> {
                        ligneIndexToLigneAffichageToTypeLigneAffichageToPosition.forEach((integer, ligneAffichageMapMap) -> {
                            ligneAffichageMapMap.forEach((ligneAffichage, typeLigneAffichageListMap) -> {
                                typeLigneAffichageListMap.forEach((typeLigneAffichage, blocAndPosition) -> {
                                    blocAndPosition.forEach(blocAffichageVector3Map -> {
                                        blocAffichageVector3Map
                                                .forEach(MainGameScreenUtil.createGameBlocEntity(size, entities, typeLigneAffichage, _json));
                                    });
                                });
                            });
                        });
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Float position = MainGameScreenUtil.getMaxBlocPosition(_entityList);
            LigneAffichage ligneAffichageCree = _intEnvironnementManager.creationLigne();
            IntStream.range(0, ligneAffichageCree.getBlocList().size())
                    .forEach(value -> {
                        BlocAffichage blocToCreate = ligneAffichageCree.getBlocList().get(value);
                        Entity entity = MainGameScreenUtil.generateEntity(blocToCreate);

                        float widthPlayerPosition = ((value+_playerMovementManager.getPlayerWidthIndex()) * Configuration.TAILLE_BLOC.get_valeur());
                        Vector3 entityPosition = new Vector3(widthPlayerPosition, 0, position);

                        entity.sendMessage(Component.MESSAGE.INIT_GRAPHICS, _json.toJson(ligneAffichageCree.getTypeLigne()),
                                _json.toJson(entityPosition), _json.toJson(size));
                        entity.sendMessage(Component.MESSAGE.INIT_HITBOX, _json.toJson(entityPosition), _json.toJson(size));
                        entities.add(entity);
                    });
            return entities;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }

    private void loadingAssets(){
        _modelManager.loadModels(Arrays.asList("D:\\Profiles\\ldalzotto\\IdeaProjects\\MyCrossyRoad\\core\\assets\\ship.obj",
                "D:\\Profiles\\ldalzotto\\IdeaProjects\\MyCrossyRoad\\core\\assets\\block.obj",
                "D:\\Profiles\\ldalzotto\\IdeaProjects\\MyCrossyRoad\\core\\assets\\invader.obj"
        ));
    }

    public ModelBatch createModelBatch(){
        return new ModelBatch();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
