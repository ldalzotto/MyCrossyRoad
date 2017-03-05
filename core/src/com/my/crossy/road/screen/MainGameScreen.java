package com.my.crossy.road.screen;

import INTEnvironnementManager.INTEnvironnementManager;
import INTEnvironnementManager.exception.JoueurNonPlace;
import INTEnvironnementManager.interfaceManger.IINTEnvironnementManager;
import INTEnvironnementManager.modele.LigneAffichage;
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
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.EntityFactory;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.exception.MaxPositionNonDeterminee;
import com.my.crossy.road.screen.util.MainGameScreenUtil;
import com.my.crossy.road.screen.viewport.GlobalViewport;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by ldalzotto on 12/11/2016.
 */
public class MainGameScreen extends GlobalViewport implements Screen{

    private static final String TAG = MainGameScreen.class.getSimpleName();

    private Environment _environment;

    private static final int MainGameScreenHeight = 800;
    private static final int MainGameScreenWidth = 600;

    private Map<Integer, List<Entity>> _entiteEnvironnementContainer;

    private PerspectiveCamera _camera = null;
    private ModelBatch _batch = null;

    private ModelManager _modelManager = ModelManager.getInstance();

    private Json _json;

    private List<Entity> _entityList;
    private Entity _joueur;

    /**
     * L'initialisation de l'environnement est déjà effectuée dans le constructeur
     */
    private IINTEnvironnementManager _intEnvironnementManager = INTEnvironnementManager.getInstance();

    public MainGameScreen(){

        setupViewport(MainGameScreenWidth, MainGameScreenHeight);

        _json = new Json();
        _batch = new ModelBatch();

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

        _entityList.forEach(entity -> entity.update(_batch, _camera, _environment, delta));
        _joueur.update(_batch, _camera, _environment, delta);

        //on vérifie si le joueur a effectué un mouvement
        if(_joueur.get_isMoving()){
            
            List<Entity> entityToCompare = _entityList.stream().collect(Collectors.toList());
            /**
             * On supprime les blocs de l'environnement contenu dans le joueur puis on fait avancer l'environnement
             * fictif d'une demi position. Ensuite, on vérifie si il y a une collision entre le joueur
             * et l'environnement fictif
             */
            //TODO améliorer la gesion des mouvements
            entityToCompare.forEach(entity1 ->
                    entity1.sendMessage(Component.MESSAGE.ENVIRONNEMENT_FUTURE_MOVE, _json.toJson(Configuration.POSITION_MIN_ENVIRONNEMENT.get_valeur())));
            Boolean isCollision = checkCollision(_joueur, entityToCompare);
            entityToCompare.forEach(entity1 ->
                    entity1.sendMessage(Component.MESSAGE.ENVIRONNEMENT_FUTURE_MODE_REVERSE, _json.toJson(Configuration.POSITION_MIN_ENVIRONNEMENT.get_valeur())));
            if(!isCollision) {
                //le déplacement est accepté
                _entityList.forEach(entity1 ->
                        entity1.sendMessage(Component.MESSAGE.ENVIRONNEMENT_MOVE, _json.toJson(Configuration.POSITION_MIN_ENVIRONNEMENT.get_valeur())));
                try {
                    List<Entity> entities = createBlocsToLastPosition(Configuration.TAILLE_BLOC.get_valeur());
                    _entityList.addAll(entities);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Gdx.app.debug(TAG, "Collision has been detected !");
            }
            entityToCompare.clear();
            _joueur.hasMoved();
        }

        //Entity garbage collector
        List<Entity> entitiesToDestroy = _entityList.stream().filter(Entity::get_isDetroyable).collect(Collectors.toList());
        entitiesToDestroy.forEach(entity -> _entityList.remove(entity));
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
        } catch (JoueurNonPlace joueurNonPlace) {
            joueurNonPlace.printStackTrace();
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
                    .mapToObj(MainGameScreenUtil.FORMAT_TYPE_LIGNE_AFFICHAGE(ligneAffichages))
                    .map(MainGameScreenUtil.DETERMINER_POSITION(Configuration.TAILLE_BLOC.get_valeur()))
                    .forEach(ligneIndexToLigneAffichageToTypeLigneAffichageToPosition -> {
                        ligneIndexToLigneAffichageToTypeLigneAffichageToPosition.forEach((integer, ligneAffichageMapMap) -> {
                            ligneAffichageMapMap.forEach((ligneAffichage, typeLigneAffichageListMap) -> {
                                typeLigneAffichageListMap.forEach((typeLigneAffichage, blocAndPosition) -> {
                                    blocAndPosition.forEach(blocAffichageVector3Map -> {
                                        blocAffichageVector3Map.forEach((blocAffichage, vector3) -> {
                                            Entity entity = null;
                                            if(blocAffichage.isAnObstacle()){
                                                entity = EntityFactory.getEntity(Entity.EntityType.BLOC_OBSTACLE);
                                            } else {
                                                entity = EntityFactory.getEntity(Entity.EntityType.BLOC_DECOR);
                                            }
                                            entity.sendMessage(Component.MESSAGE.INIT_GRAPHICS, _json.toJson(typeLigneAffichage),
                                                    _json.toJson(vector3), _json.toJson(size));
                                            entity.sendMessage(Component.MESSAGE.INIT_HITBOX, _json.toJson(vector3),
                                                    _json.toJson(size));
                                            entities.add(entity);
                                        });
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
            Float position = getMaxBlocPosition();
            LigneAffichage ligneAffichageCree = _intEnvironnementManager.creationLigne();
            IntStream.range(0, ligneAffichageCree.get_blocList().size())
                    .forEach(value -> {
                        Entity entity = null;

                        if(ligneAffichageCree.get_blocList().get(value).isAnObstacle()){
                            entity = EntityFactory.getEntity(Entity.EntityType.BLOC_OBSTACLE);
                        } else {
                            entity = EntityFactory.getEntity(Entity.EntityType.BLOC_DECOR);
                        }

                        Vector3 entityPosition = new Vector3(value * Configuration.TAILLE_BLOC.get_valeur(), 0, position);
                        entity.sendMessage(Component.MESSAGE.INIT_GRAPHICS, _json.toJson(ligneAffichageCree.get_typeLigne()),
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

    /**
     * Permet de retourner la position maximale des lignes de blocs actuels
     * @return la position maximale des lignes de blocs
     * @throws MaxPositionNonDeterminee
     */
    private Float getMaxBlocPosition() throws MaxPositionNonDeterminee{
        Optional<Float> position =_entityList.stream()
                .max((o1, o2) -> {
                    Float value = o1.get_position().z - o2.get_position().z;
                    if(value >= 0){
                        return 1;
                    }else{
                        return -1;
                    }
                }).map(entity -> entity.get_position().z);
        if(position.isPresent()){
            return position.get();
        } else {
            throw new MaxPositionNonDeterminee("La position maximale des entités n'a pas pu être déterminé", null);
        }
    }

    private void doneLoading(){

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

    /**
     * Vérifie si l'entité renseignée en entrée rentre en collision avec les blocs
     * @param entityATester l'entité pour laquelle nous voulons vérifier si elle rentre en collision
     * @param listeEntiteAComparer la liste d'entité contre laquelle nous voulons check la collision
     * @return true = collision, false = pas de collision
     */
    private Boolean checkCollision(Entity entityATester, List<Entity> listeEntiteAComparer){
        return listeEntiteAComparer.stream()
                .map(Entity::get_physicsComponent)
                .filter(physicsComponent -> physicsComponent!=null)
                .map(physicsComponent -> physicsComponent.isInCollitionWith(entityATester))
                .filter(aBoolean -> aBoolean)
                .findFirst().orElse(false);
    }

    /**
     * Afin de détecter les collisions, il est nécessaire de ne pas prendre en compte le bloc
     * sous le joueur. En effet, les collisions s'effectuent sur un plan 2D vue du dessus de l'environnement
     * @return la liste d'entité située à la même position que le joueur
     */
    private List<Entity> getJoueurActualEnvironnementBloc(){
        return _entityList.stream()
                .filter(entity -> {
                    return (entity.get_position().x == _joueur.get_position().x &&
                            entity.get_position().z == _joueur.get_position().z);
                })
                .collect(Collectors.toList());
    }

}
