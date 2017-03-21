package calcul;

import enumeration.Configuration;
import enumeration.TypeBloc;
import exception.EssaiTropNombreuxSurCreationPosition;
import exception.LigneNonRenseignee;
import modele.Bloc;
import modele.Environnement;
import modele.Ligne;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;

/**
 * Created by ldalzotto on 20/02/2017.
 */
public class INTCalculEnvironnementUtil {

    INTCalculEnvironnementUtil(){
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Ajout d'une ligne de {@link Environnement} depuis celle classe utilitaire
     * @param environnement la ligne est insérée dans le paramètre environnement sous la méthode {@link Environnement#ajoutLigne(Ligne)}
     * @return le consumer
     */
    public static Consumer<Ligne> AJOUT_LIGNE(Environnement environnement){
        return ((ligne -> {
            try {
                environnement.ajoutLigne(ligne);
            } catch (LigneNonRenseignee ligneNonRenseignee) {
                ligneNonRenseignee.printStackTrace();
            }
        }));
    }

    public static IntConsumer CREATION_BLOC_CHEMIN(int minPositionOuverture, int maxPositionOuverture,
                                                    List<Bloc> blocs){
        return i1 -> {
            if(i1 == minPositionOuverture || i1 == maxPositionOuverture){
                blocs.set(i1, new Bloc(TypeBloc.Decor, true));
            } else {
                blocs.set(i1, new Bloc(TypeBloc.Decor, false));
            }
        };
    }

    public static final Function<Integer, Integer> SET_MIN_0 = integer -> {
        if(integer < 0){
            System.out.println("Valeur "+ integer +" réduite à la valeur minimale 0");
            return 0;
        } else {
            return integer;
        }
    };

    public static Function<Integer, Integer> SET_MAX_AS_LARGEUR = integer -> {
        if(integer >= Configuration.EnvironnementLargeur.get_valeur()){
            System.out.println("Valeur "+ integer + " réduite à la valeur maximale " + Configuration.EnvironnementLargeur.get_valeur());
            return Configuration.EnvironnementLargeur.get_valeur()-1;
        } else {
            return integer;
        }
    };

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
    public static IntConsumer CREATION_POSITION_UNIQUE_SUR_ETENDUE(List<Integer> positionOuvertures, List<Integer> etendue) throws RuntimeException{
        return value -> {
            try {
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
            } catch (EssaiTropNombreuxSurCreationPosition essaiTropNombreuxSurCreationPosition) {
                throw new RuntimeException(essaiTropNombreuxSurCreationPosition);
            }
        };
    }

}
