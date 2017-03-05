package com.my.crossy.road.screen.util;

import INTEnvironnementManager.enumeration.TypeLigneAffichage;
import INTEnvironnementManager.modele.BlocAffichage;
import INTEnvironnementManager.modele.LigneAffichage;
import com.badlogic.gdx.math.Vector3;
import com.my.crossy.road.exception.TypeLigneNonReconnu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 05/03/2017.
 */
public class MainGameScreenUtil {

    /**
     * Permet de remonter le {@link TypeLigneAffichage} et son index depuis l'index de la {@link LigneAffichage}
     * @param ligneAffichages les lignes d'affichages à déterminer
     * @return le fonction consommatrice de récupération
     * @throws TypeLigneNonReconnu si la ligne n'a pas pu être identifée
     */
    public static IntFunction<Map<TypeLigneAffichage, Integer>> TYPE_LIGNE_AFFICHAGE_DEPUIS_LIGNE_AFFICHAGE(List<LigneAffichage> ligneAffichages)
            throws TypeLigneNonReconnu{
        IntFunction<Map<TypeLigneAffichage, Integer>> mapIntFunction = null;
        try {
            mapIntFunction = value -> {
                LigneAffichage currentLigneAffichage = ligneAffichages.get(value);
                Map<TypeLigneAffichage, Integer> typeLigneAffichageLigneAffichageMap = new HashMap<>();
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
    public static IntFunction<Map<Integer, Map<LigneAffichage, TypeLigneAffichage>>> FORMAT_TYPE_LIGNE_AFFICHAGE(List<LigneAffichage> ligneAffichages)
            {
        IntFunction<Map<Integer, Map<LigneAffichage, TypeLigneAffichage>>> mapIntFunction = null;
        mapIntFunction = indexValue -> {
            LigneAffichage currentLigneAffichage = ligneAffichages.get(indexValue);
            //initialisation de la map
            Map<Integer, Map<LigneAffichage, TypeLigneAffichage>> mapIndexToLigneAffichageToTypeLigneAffichage = new HashMap<>();
            Map<LigneAffichage, TypeLigneAffichage> mapLigneAffichageToTypeLigneAffichage = new HashMap<>();
            mapLigneAffichageToTypeLigneAffichage.put(ligneAffichages.get(indexValue), ligneAffichages.get(indexValue).get_typeLigne());
            mapIndexToLigneAffichageToTypeLigneAffichage.put(indexValue, mapLigneAffichageToTypeLigneAffichage);
            return mapIndexToLigneAffichageToTypeLigneAffichage;
        };
        return mapIntFunction;
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
                                DETERMINER_POSITION(Float size){
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
                                    //initialisation
                                    Map<TypeLigneAffichage, List<Map<BlocAffichage, Vector3>>> mapTypeLigneAffichageToPosition = new HashMap<>();
                                    mapTypeLigneAffichageToPosition.put(typeLigneAffichage, new ArrayList<>());
                                    Map<LigneAffichage, Map<TypeLigneAffichage, List<Map<BlocAffichage, Vector3>>>> mapLigneAffichageToTypeLigneAffichageToPosition = new HashMap<>();
                                    mapLigneAffichageToTypeLigneAffichageToPosition.put(ligneAffichage, mapTypeLigneAffichageToPosition);
                                    ligneIndexToLigneAffichageToTypeLigneAffichageToPosition.get(index).put(ligneAffichage, mapTypeLigneAffichageToPosition);


                                    List<BlocAffichage> blocAffichages = ligneAffichage.get_blocList();
                                    IntStream.range(0, blocAffichages.size())
                                            .forEach(blocAffichagesIndex -> {
                                                //mise à jour ou initialisation de la liste des positions
                                                List<Map<BlocAffichage, Vector3>> positionsList = ligneIndexToLigneAffichageToTypeLigneAffichageToPosition.get(index).get(ligneAffichage).get(typeLigneAffichage);
                                                Map<BlocAffichage, Vector3> mapBlocAffichageToVector = new HashMap<>();
                                                mapBlocAffichageToVector.put(blocAffichages.get(blocAffichagesIndex), new Vector3(blocAffichagesIndex*size, 0, index*size));
                                                positionsList.add(mapBlocAffichageToVector);
                                                ligneIndexToLigneAffichageToTypeLigneAffichageToPosition.get(index).get(ligneAffichage).put(typeLigneAffichage, positionsList);
                                            });
                                });
                        });
                return ligneIndexToLigneAffichageToTypeLigneAffichageToPosition;
             };
        }

}
