package com.my.crossy.road.screen.util;

import INTEnvironnementManager.enumeration.TypeLigneAffichage;
import INTEnvironnementManager.modele.BlocAffichage;
import INTEnvironnementManager.modele.LigneAffichage;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.EntityFactory;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.exception.BadInstance;
import com.my.crossy.road.exception.MaxPositionNonDeterminee;
import com.my.crossy.road.exception.TypeLigneNonReconnu;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 05/03/2017.
 */
public class MainGameScreenUtil {

    private MainGameScreenUtil() throws BadInstance{
        throw new BadInstance("This class cannot be instanciated !", null);
    }

    /**
     * Permet de remonter le {@link TypeLigneAffichage} et son index depuis l'index de la {@link LigneAffichage}
     * @param ligneAffichages les lignes d'affichages à déterminer
     * @return le fonction consommatrice de récupération
     * @throws TypeLigneNonReconnu si la ligne n'a pas pu être identifée
     */
    public static IntFunction<Map<TypeLigneAffichage, Integer>> typeLigneAffichageDepuisLigneAffichage(List<LigneAffichage> ligneAffichages)
            throws TypeLigneNonReconnu{
        IntFunction<Map<TypeLigneAffichage, Integer>> mapIntFunction = null;
        try {
            mapIntFunction = value -> {
                LigneAffichage currentLigneAffichage = ligneAffichages.get(value);
                Map<TypeLigneAffichage, Integer> typeLigneAffichageLigneAffichageMap = new EnumMap<>(TypeLigneAffichage.class);
                typeLigneAffichageLigneAffichageMap.put(currentLigneAffichage.get_typeLigne(), value);
                return typeLigneAffichageLigneAffichageMap;
            };
        } catch (Exception e) {
            throw new TypeLigneNonReconnu("La ligne créé n'a pas été identifiée", e);
        }
        return mapIntFunction;
    }

    /**
     * Depuis une liste de {@link LigneAffichage}, permet de renvoyer le triplet Index, {@link LigneAffichage} et {@link TypeLigneAffichage}
     * @param ligneAffichages la liste le {@link LigneAffichage} que l'on souhaite formatter
     * @return la fonction de calcul
     */
    public static IntFunction<Map<Integer, Map<LigneAffichage, TypeLigneAffichage>>> formatTypeLigneAffichage(List<LigneAffichage> ligneAffichages)
            {
        return indexValue -> {
            //initialisation de la map
            Map<Integer, Map<LigneAffichage, TypeLigneAffichage>> mapIndexToLigneAffichageToTypeLigneAffichage = new HashMap<>();
            Map<LigneAffichage, TypeLigneAffichage> mapLigneAffichageToTypeLigneAffichage = new HashMap<>();
            mapLigneAffichageToTypeLigneAffichage.put(ligneAffichages.get(indexValue), ligneAffichages.get(indexValue).get_typeLigne());
            mapIndexToLigneAffichageToTypeLigneAffichage.put(indexValue, mapLigneAffichageToTypeLigneAffichage);
            return mapIndexToLigneAffichageToTypeLigneAffichage;
        };
    }



    /**
     * Permet de déterminer la position des {@link BlocAffichage} de lignesAffichages sous forme de {@link Vector3}
     * Le format de sortie permet de déterminer dans l'ordre les élément suivants :
     * - Index de la ligne
     * - {@link LigneAffichage}
     * - {@link TypeLigneAffichage}
     * - Pour tous les blocs -> List<{@link BlocAffichage}, Position>
     * @param size la taille des blocs
     * @return la fonction permettant de fournir cette fonctionnalité
     */
    public static Function<Map<Integer, Map<LigneAffichage, TypeLigneAffichage>>, Map<Integer, Map<LigneAffichage, Map<TypeLigneAffichage, List<Map<BlocAffichage, Vector3>>>>>>
                                    determinerPosition(Float size){
        return mapIndexToLigneAffichageToTypeLigneAffichage -> {
            //initialisation de la map à retourner
            Map<Integer, Map<LigneAffichage, Map<TypeLigneAffichage, List<Map<BlocAffichage, Vector3>>>>>
                    ligneIndexToLigneAffichageToTypeLigneAffichageToPosition = new HashMap<>();

            //pour chaque index
            mapIndexToLigneAffichageToTypeLigneAffichage.keySet()
                    //on récupère la liste de blocsAffichage pour chaque couple LigneAffichage & TypeLigneAffichage
                    .forEach(index -> {
                        ligneIndexToLigneAffichageToTypeLigneAffichageToPosition.put(index, new HashMap<>());

                        mapIndexToLigneAffichageToTypeLigneAffichage.get(index)
                                .forEach((ligneAffichage, typeLigneAffichage) -> {
                                    mapInitialisation(ligneIndexToLigneAffichageToTypeLigneAffichageToPosition, index,
                                            ligneAffichage, typeLigneAffichage);

                                    List<BlocAffichage> blocAffichages = ligneAffichage.get_blocList();
                                    IntStream.range(0, blocAffichages.size())
                                            .forEach(blocAffichagesIndex ->
                                                updatePositions(size, ligneIndexToLigneAffichageToTypeLigneAffichageToPosition,
                                                        index, ligneAffichage, typeLigneAffichage, blocAffichages, blocAffichagesIndex)
                                            );
                                });
                        });
                return ligneIndexToLigneAffichageToTypeLigneAffichageToPosition;
             };
        }

    /**
     * Permet de mettre à jour la map de position ligneIndexToLigneAffichageToTypeLigneAffichageToPosition.
     * @param size la taille du bloc à créer
     * @param ligneIndexToLigneAffichageToTypeLigneAffichageToPosition la map actuelle à mettre à jour
     * @param index l'index actuel de la ligne
     * @param ligneAffichage la ligne d'affichage actuelle
     * @param typeLigneAffichage le type de ligne affichage actuel
     * @param blocAffichages la liste des blocs de la ligneAffichage
     * @param blocAffichagesIndex l'index actuel du bloc
     */
    private static void updatePositions(Float size, Map<Integer, Map<LigneAffichage, Map<TypeLigneAffichage, List<Map<BlocAffichage, Vector3>>>>> ligneIndexToLigneAffichageToTypeLigneAffichageToPosition,
                                        Integer index, LigneAffichage ligneAffichage, TypeLigneAffichage typeLigneAffichage,
                                        List<BlocAffichage> blocAffichages, int blocAffichagesIndex) {
        //mise à jour ou initialisation de la liste des positions
        List<Map<BlocAffichage, Vector3>> positionsList = ligneIndexToLigneAffichageToTypeLigneAffichageToPosition.get(index).get(ligneAffichage).get(typeLigneAffichage);
        Map<BlocAffichage, Vector3> mapBlocAffichageToVector = new HashMap<>();
        mapBlocAffichageToVector.put(blocAffichages.get(blocAffichagesIndex), new Vector3(blocAffichagesIndex*size, 0, index*size));
        positionsList.add(mapBlocAffichageToVector);
        ligneIndexToLigneAffichageToTypeLigneAffichageToPosition.get(index).get(ligneAffichage).put(typeLigneAffichage, positionsList);
    }

    /**
     * Permet d'initialiser la map finale ligneIndexToLigneAffichageToTypeLigneAffichageToPosition avec les valeurs associées
     * en entrée
     * @param ligneIndexToLigneAffichageToTypeLigneAffichageToPosition la map à initialiser
     * @param index l'index de la ligne
     * @param ligneAffichage la ligne associé à l'index
     * @param typeLigneAffichage le type de ligne associé à la ligne
     */
    private static void mapInitialisation(Map<Integer, Map<LigneAffichage, Map<TypeLigneAffichage, List<Map<BlocAffichage, Vector3>>>>> ligneIndexToLigneAffichageToTypeLigneAffichageToPosition,
                                          Integer index, LigneAffichage ligneAffichage, TypeLigneAffichage typeLigneAffichage) {
        //initialisation
        Map<TypeLigneAffichage, List<Map<BlocAffichage, Vector3>>> mapTypeLigneAffichageToPosition = new EnumMap<>(TypeLigneAffichage.class);
        mapTypeLigneAffichageToPosition.put(typeLigneAffichage, new ArrayList<>());
        Map<LigneAffichage, Map<TypeLigneAffichage, List<Map<BlocAffichage, Vector3>>>> mapLigneAffichageToTypeLigneAffichageToPosition = new HashMap<>();
        mapLigneAffichageToTypeLigneAffichageToPosition.put(ligneAffichage, mapTypeLigneAffichageToPosition);
        ligneIndexToLigneAffichageToTypeLigneAffichageToPosition.get(index).put(ligneAffichage, mapTypeLigneAffichageToPosition);
    }

    /**
     * Permet de créer une entité de type BLOC_OBSTACLE ou BLOC_DECOR
     * @param size la taille de l'entité
     * @param entities la liste d'entités globale du système
     * @param typeLigneAffichage le type de ligne
     * @param json paramètre json
     * @return la fonction de calcul
     */
    public static BiConsumer<BlocAffichage, Vector3> createGameBlocEntity(Float size, List<Entity> entities, TypeLigneAffichage typeLigneAffichage,
                                                                    Json json) {
        return (blocAffichage, vector3) -> {
            Entity entity = generateEntity(blocAffichage);
            entity.sendMessage(Component.MESSAGE.INIT_GRAPHICS, json.toJson(typeLigneAffichage),
                    json.toJson(vector3), json.toJson(size));
            entity.sendMessage(Component.MESSAGE.INIT_HITBOX, json.toJson(vector3),
                    json.toJson(size));
            entities.add(entity);
        };
    }

    public static Entity generateEntity(BlocAffichage blocAffichage) {
        Entity entity = null;
        if(blocAffichage.isAnObstacle()){
            entity = EntityFactory.getEntity(Entity.EntityType.BLOC_OBSTACLE);
        } else if(blocAffichage.isAPhantomObstacle()){
            entity = EntityFactory.getEntity(Entity.EntityType.BLOC_OBSTACLE_INVISIBLE);
        } else {
            entity = EntityFactory.getEntity(Entity.EntityType.BLOC_DECOR);
        }
        return entity;
    }

    /**
     * Vérifie si l'entité renseignée en entrée rentre en collision avec les blocs
     * @param entityATester l'entité pour laquelle nous voulons vérifier si elle rentre en collision
     * @param listeEntiteAComparer la liste d'entité contre laquelle nous voulons check la collision
     * @return true = collision, false = pas de collision
     */
    public static Boolean checkCollision(Entity entityATester, List<Entity> listeEntiteAComparer){
        return listeEntiteAComparer.stream()
                .map(Entity::get_physicsComponent)
                .filter(Objects::nonNull)
                .map(physicsComponent -> physicsComponent.isInCollitionWith(entityATester))
                .filter(aBoolean -> aBoolean)
                .findFirst().orElse(false);
    }


    /**
     * Permet de retourner la position des dernières entités de l'environnement fournies en entrée de cette méthode
     * @param  listeBloc la liste des entités que l'on souhaite avoir la position max
     * @return la position maximale des lignes de blocs
     * @throws MaxPositionNonDeterminee
     */
    public static Float getMaxBlocPosition(List<Entity> listeBloc) throws MaxPositionNonDeterminee{
        Optional<Float> position =listeBloc.stream()
                .filter(entity -> entity.get_position() != null)
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


}
