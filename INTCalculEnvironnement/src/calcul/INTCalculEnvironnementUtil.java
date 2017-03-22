package calcul;

import enumeration.Configuration;
import enumeration.TypeBloc;
import exception.EssaiTropNombreuxSurCreationPosition;
import exception.LigneNonRenseignee;
import logging.LoggerCalculEnvironnement;
import modele.Bloc;
import modele.Environnement;
import modele.Ligne;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ldalzotto on 20/02/2017.
 */
class INTCalculEnvironnementUtil {

    private static final Logger LOGGER = Logger.getLogger(INTCalculEnvironnementUtil.class.getSimpleName());

    static Function<Integer, Integer> setMaxAsLargeur = integer -> {
        if(integer >= Configuration.EnvironnementLargeur.get_valeur()){
            LoggerCalculEnvironnement.log(LOGGER, Level.FINEST, "Valeur %s réduite à la valeur maximale %s", integer, Configuration.EnvironnementLargeur.get_valeur());
            return Configuration.EnvironnementLargeur.get_valeur()-1;
        } else {
            return integer;
        }
    };

    static final Function<Integer, Integer> SET_MIN_0 = integer -> {
        if(integer < 0){
            LoggerCalculEnvironnement.log(LOGGER, Level.FINEST, "Valeur %s réduite à la valeur minimale 0", integer);
            return 0;
        } else {
            return integer;
        }
    };

    INTCalculEnvironnementUtil(){
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Ajout d'une ligne de {@link Environnement} depuis celle classe utilitaire
     * @param environnement la ligne est insérée dans le paramètre environnement sous la méthode {@link Environnement#ajoutLigne(Ligne)}
     * @return le consumer
     */
    static Consumer<Ligne> ajoutLigne(Environnement environnement){
        return ligne -> {
            try {
                environnement.ajoutLigne(ligne);
            } catch (LigneNonRenseignee ligneNonRenseignee) {
                LOGGER.log(Level.SEVERE, ligneNonRenseignee.getMessage(), ligneNonRenseignee);
            }
        };
    }

    /**
     * Un consumer qui permet de créer un bloc d'ouverture entre minPositionOuverture et maxPositionOuverture
     * dans la liste de blocs
     * @param minPositionOuverture l'index min d'ouverture de la ligne
     * @param maxPositionOuverture l'index max d'ouverture de la ligne
     * @param blocs la liste des blocs où l'on souhaite créer le bloc
     * @return le consumer
     */
    static IntConsumer creationBlocChemin(int minPositionOuverture, int maxPositionOuverture,
                                          List<Bloc> blocs){
        return i1 -> {
            if(i1 == minPositionOuverture || i1 == maxPositionOuverture){
                blocs.set(i1, new Bloc(TypeBloc.Decor, true));
            } else {
                blocs.set(i1, new Bloc(TypeBloc.Decor, false));
            }
        };
    }



    /**
     * Ajoute une position supplémentaire (Integer) dans la liste des positionOuvertures. La position ajoutée sera toujours
     * unique. La position est crée à l'intérieur de l'étendue.
     * Si le nombre d'essai d'insertion est trop important (par soucis de robustesse ou de manque de place
     * insufisante dans positionOuver), l'exception {@link EssaiTropNombreuxSurCreationPosition} est renvoyée.
     * @param positionOuvertures liste des positions d'ouvertures actuelles sur laquelle nous voulons insérer notes
     *                           position
     * @param etendue étendue sur laquelle la position sera créée
     * @return Lambda expression
     * @throws RuntimeException exception durnant l'exécution de la méthode
     */
    static IntConsumer creationPositionUniqueSurEtendue(List<Integer> positionOuvertures, List<Integer> etendue) throws EssaiTropNombreuxSurCreationPosition{
        return  value -> {
                    int essaiInitialisationPosition = 1;
                    int position = ThreadLocalRandom.current().nextInt(etendue.get(0), etendue.get(1) + 1);
                    while (positionOuvertures.contains(position)) {
                        position = ThreadLocalRandom.current().nextInt(etendue.get(0), etendue.get(1) + 1);
                        essaiInitialisationPosition ++;
                        if(essaiInitialisationPosition > 10){
                            throw new EssaiTropNombreuxSurCreationPosition("Le nombre d'essai d'insertion est trop important", null);
                        }
                    }
                    positionOuvertures.add(position);
            };


    }

}
