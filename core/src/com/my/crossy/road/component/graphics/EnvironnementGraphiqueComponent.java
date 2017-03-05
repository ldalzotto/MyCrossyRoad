package com.my.crossy.road.component.graphics;

import INTEnvironnementManager.INTEnvironnementManager;
import INTEnvironnementManager.enumeration.TypeLigneAffichage;
import INTEnvironnementManager.interfaceManger.IINTEnvironnementManager;
import INTEnvironnementManager.modele.BlocAffichage;
import INTEnvironnementManager.modele.LigneAffichage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.GraphicsComponent;
import com.my.crossy.road.exception.TypeLigneNonReconnu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 25/02/2017.
 */
public class EnvironnementGraphiqueComponent extends GraphicsComponent {

    private static final String TAG = EnvironnementGraphiqueComponent.class.getSimpleName();
    private Json _json = new Json();

    private IINTEnvironnementManager _intEnvironnementManager = INTEnvironnementManager.getInstance();

    //TODO les modèles doivent être dans le ModelManager
    private Model _treeModel;
    private Model _routeModel;
    private Model _eauModel;
    private Model _trainModel;

    @Override
    public void receiveMessage(String message) {
        String[] messageReceived = message.split(Component.MESSAGE_TOKEN);

        if(messageReceived.length > 1){
            if(messageReceived[0].equalsIgnoreCase(MESSAGE.INIT_GRAPHICS.toString())){
                Gdx.app.debug(TAG, "Message " + MESSAGE.INIT_GRAPHICS.toString() + " reveived.");
                ModelBuilder modelBuilder = new ModelBuilder();
                _treeModel = modelBuilder.createBox(5f, 5f, 5f, new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                _eauModel = modelBuilder.createBox(5f, 5f, 5f, new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                _routeModel = modelBuilder.createBox(5f, 5f, 5f, new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
                _trainModel = modelBuilder.createBox(5f, 5f, 5f, new Material(ColorAttribute.createDiffuse(Color.RED)),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

            }
        }
    }

    @Override
    public void update(Entity entity, ModelBatch batch, Camera camera, Environment environment) {

        try {
            List<LigneAffichage> ligneAffichages = _intEnvironnementManager.getEnvironnementLignesPourAffichage().collect(Collectors.toList());
            IntStream.range(0, ligneAffichages.size())
                    .mapToObj(MODELE_DEPUIS_TYPE_LIGNE(ligneAffichages))
                    .map(modelInstanceLigneAffichageHashMap -> {
                        Map<ModelInstance, List<BlocAffichage>> modelInstanceListMap = new HashMap<ModelInstance, List<BlocAffichage>>();
                        modelInstanceLigneAffichageHashMap.keySet().iterator().forEachRemaining(modelInstance -> {
                            modelInstanceListMap.put(modelInstance, modelInstanceLigneAffichageHashMap.get(modelInstance).get_blocList());
                        });
                        return modelInstanceListMap;
                    })
                    .forEach(DESSINE_BLOCS_LIGNE(batch, camera));
        } catch (Exception ex) {
            ex.printStackTrace();
            Gdx.app.error(TAG, ex.getMessage());
        }

    }

    /**
     * Permet de retourner le modèle de cube en fonction du type ligne de la {@link LigneAffichage}.
     * Le modèle affiché dépend uniquement du type ligne de la {@link LigneAffichage}.
     * @param ligneAffichages la liste des lignes d'affichage à afficher
     * @return la fonction permettant de renvoyer le modèle en fonction de la ligne d'affichage
     * @throws TypeLigneNonReconnu quand la ligne n'a pas été correctement identifiée
     */
    private IntFunction<Map<ModelInstance, LigneAffichage>> MODELE_DEPUIS_TYPE_LIGNE(List<LigneAffichage> ligneAffichages)
                    throws TypeLigneNonReconnu{
        IntFunction<Map<ModelInstance, LigneAffichage>> mapIntFunction = null;
        try {
            mapIntFunction = value -> {
                LigneAffichage currentLigneAffichage = ligneAffichages.get(value);
                if(currentLigneAffichage.get_typeLigne().equals(TypeLigneAffichage.Arbre)) {
                    HashMap<ModelInstance, LigneAffichage> modelLigneAffichageHashMap = new HashMap<ModelInstance, LigneAffichage>();
                    ModelInstance modelInstance = new ModelInstance(_treeModel);
                    modelInstance.transform.translate(new Vector3(0, 0, value * 5));
                    modelLigneAffichageHashMap.put(modelInstance, currentLigneAffichage);
                    return modelLigneAffichageHashMap;
                } else if(currentLigneAffichage.get_typeLigne().equals(TypeLigneAffichage.Eau)){
                    HashMap<ModelInstance, LigneAffichage> modelLigneAffichageHashMap = new HashMap<ModelInstance, LigneAffichage>();
                    ModelInstance modelInstance = new ModelInstance(_eauModel);
                    modelInstance.transform.translate(new Vector3(0, 0, value * 5));
                    modelLigneAffichageHashMap.put(modelInstance, currentLigneAffichage);
                    return modelLigneAffichageHashMap;
                } else if(currentLigneAffichage.get_typeLigne().equals(TypeLigneAffichage.Route)){
                    HashMap<ModelInstance, LigneAffichage> modelLigneAffichageHashMap = new HashMap<ModelInstance, LigneAffichage>();
                    ModelInstance modelInstance = new ModelInstance(_routeModel);
                    modelInstance.transform.translate(new Vector3(0, 0, value * 5));
                    modelLigneAffichageHashMap.put(modelInstance, currentLigneAffichage);
                    return modelLigneAffichageHashMap;
                }else if(currentLigneAffichage.get_typeLigne().equals(TypeLigneAffichage.Train)){
                    HashMap<ModelInstance, LigneAffichage> modelLigneAffichageHashMap = new HashMap<ModelInstance, LigneAffichage>();
                    ModelInstance modelInstance = new ModelInstance(_trainModel);
                    modelInstance.transform.translate(new Vector3(0, 0, value * 5));
                    modelLigneAffichageHashMap.put(modelInstance, currentLigneAffichage);
                    return modelLigneAffichageHashMap;
                } else {
                    throw new RuntimeException("La ligne créé n'a pas été identifiée", null);
                }
            };
        } catch (Exception e) {
            throw new TypeLigneNonReconnu("La ligne créé n'a pas été identifiée", e);
        }
        return mapIntFunction;
    }

    /**
     * Permet d'afficher à l'écran tous les {@link BlocAffichage} d'une ligne. Si le {@link BlocAffichage} est une ouverture,
     * alors il sera affiché en blanc. Egalement, les blocs sont espacés de 5 car c'est la taille des blocs initialiement
     * créées. TODO rendre la taille des blocs paramétrables
     * @param batch le batch d'affichage libgdx
     * @param camera la caméra actuelle
     * @return la fonction d'affichage
     */
    private Consumer<Map<ModelInstance, List<BlocAffichage>>> DESSINE_BLOCS_LIGNE(ModelBatch batch, Camera camera){
        return modelInstanceListMap -> {
            modelInstanceListMap.keySet().iterator().forEachRemaining(modelInstance -> {
                IntStream.range(0, modelInstanceListMap.get(modelInstance).size())
                        .forEach(value -> {
                            ModelInstance instance = modelInstance.copy();
                            instance.transform.translate(new Vector3(value * 5, 0,0));
                            BlocAffichage currentBloc = modelInstanceListMap.get(modelInstance).get(value);
                            if(currentBloc.getIsOuverture()){
                                instance.materials.get(0).set(ColorAttribute.createDiffuse(Color.WHITE));
                            }
                            batch.begin(camera);
                            batch.render(instance);
                            batch.end();
                        });
            });
        };
    }

}
