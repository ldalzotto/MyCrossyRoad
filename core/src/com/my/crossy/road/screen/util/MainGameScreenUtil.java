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
     * Permet de déterminer la position des {@link BlocAffichage} de lignesAffichages sous forme de {@link Vector3}
     * la détermination des positions s'effectue dans l'ordre de la ligneAffichages
     * @param ligneAffichages la liste de lignes pour lesquelles on veut détemriner la position des blocs
     * @param size la taille des blocs
     * @return la fonction permettant de fournir cette fonctionnalité
     */
    public static Function<Map<TypeLigneAffichage,Integer>, Map<TypeLigneAffichage, List<Vector3>>> DETERMINER_POSITION(List<LigneAffichage> ligneAffichages,
                                                                                                                  Float size){
        return typeLigneAffichageIntegerMap -> {
            Map<TypeLigneAffichage, List<Vector3>> typeLigneAffichageVector3Map = new HashMap<>();
            typeLigneAffichageIntegerMap.keySet()
                    .forEach(typeLigneAffichage -> {
                        Integer index = typeLigneAffichageIntegerMap.get(typeLigneAffichage);
                        List<BlocAffichage> blocAffichages =ligneAffichages.get(index).get_blocList();
                        IntStream.range(0, blocAffichages.size())
                                .forEach(value -> {
                                    List<Vector3> vector3List;
                                    if(typeLigneAffichageVector3Map.get(typeLigneAffichage) != null){
                                        vector3List = typeLigneAffichageVector3Map.get(typeLigneAffichage);
                                    } else {
                                        vector3List = new ArrayList<Vector3>();
                                    }
                                    vector3List.add(new Vector3(value*size, 0, index*size));
                                    typeLigneAffichageVector3Map.put(typeLigneAffichage, vector3List);
                                });

                    });
            return typeLigneAffichageVector3Map;
        };
    }

}
