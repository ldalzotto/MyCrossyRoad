package calcul;

import calcul.interfaces.IINTCalculEnvironnement;
import common.enumeration.TypeLigne;
import enumeration.Configuration;
import enumeration.EnvironnementInit;
import enumeration.TypeBloc;
import exception.EnvironnementLigneNonRenseignee;
import exception.LigneNonCree;
import logging.LoggerCalculEnvironnement;
import modele.Bloc;
import modele.Environnement;
import modele.Ligne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class INTCalculEnvironnement implements IINTCalculEnvironnement {

    private static final Logger LOGGER = Logger.getLogger(INTCalculEnvironnement.class.getSimpleName());

    private Environnement environnement;
    private boolean nbOuvertureModifiable;
    private int nbLignesAjoute;

    public INTCalculEnvironnement(){
        environnement = new Environnement();
        nbOuvertureModifiable = true;
        nbLignesAjoute = 0;
    }

    @Override
    public void initialisationEnvironnement() {
            IntStream.range(0, EnvironnementInit.values().length)
                    .mapToObj(index -> EnvironnementInit.values()[index].getLigne())
                    .forEach(INTCalculEnvironnementUtil.ajoutLigne(environnement));
    }

    @Override
    public Ligne creationLigne() throws LigneNonCree{
        int menace = ThreadLocalRandom.current().nextInt(0,2);

        //type de ligne
        TypeLigne typeLigne = TypeLigne.getTypeAleatoire(menace);

        //popule bloc
        List<Bloc> blocs;
        if(menace == 0){
            blocs = populeBlocs(TypeBloc.OBSTACLE);
        }else {
            blocs = populeBlocs(TypeBloc.DECOR);
        }

        try {
            Ligne ligneActuelle = getLigneActuelle().copy();
            removePhatomCollisionBlocsFromLigne(ligneActuelle);

            //détermine nombre d'ouverture pour la ligne suivante
            int nombreOuverture = calculNombreOuverture(ligneActuelle);
            LoggerCalculEnvironnement.log(LOGGER, Level.FINEST, "Nombre d'ouverture calculées : %s", nombreOuverture);

            //détermine position d'ouverture de la ligne actuelle
            List<Integer> ouvertures = ligneActuelle.getOuverturesIndex();
            LoggerCalculEnvironnement.log(LOGGER, Level.FINEST, "Position des ouvertures de la dernière ligne insérée : %s", ouvertures);

            //calcul de l'étendue des positions possibles
            LoggerCalculEnvironnement.log(LOGGER, Level.FINEST, "Calcul de l'étendue ...");
            List<Integer> etendue = calculEtendue(ouvertures);
            LoggerCalculEnvironnement.log(LOGGER, Level.FINEST, "Calcul de l'étendue, END. Etendue : %s" , etendue.toString());

            //calcl position des ouvertures suivantes
            List<Integer> positionOuvertures = calculPositionOverturesSuivantes(nombreOuverture, etendue);
            LoggerCalculEnvironnement.log(LOGGER, Level.FINEST, "Positions calculées : %s" , positionOuvertures);

            //relier la ligne à créer avec la précedente si nécessaire
            additionCheminEntreLigne(blocs, positionOuvertures, ouvertures);
            //création des blocs
            creationChemin(blocs, positionOuvertures);

            //ajouter des blocs de collision invisible aux extrémités de la ligne
            addPhatomCollisionBlocsAtEnd(blocs);

            Ligne ligne = new Ligne(typeLigne, blocs);
            Integer positionLigne =  environnement.ajoutLigne(ligne);
            LoggerCalculEnvironnement.log(LOGGER, Level.FINEST, "Ligne ajoutée sur la position %s" , positionLigne);

            nbLignesAjoute++;
            return ligne;
        } catch (Exception e) {
            throw new LigneNonCree("La ligne n'a pas été créé", e);
        }
    }

    /**
     * Créée le chemin nécessaire afin d'avoir une connection entre deux lignes consécutives.
     * Le chemin créé consite à relier les ouvertures une à une de chacune des lignes.
     * @param blocs la liste des blocs de la ligne actuelle. Cette liste est modifiée en sortie
     * @param positionOuvertures positions des ouvertures de la nouvelle ligne
     * @param ouvertures positions des ouvertures de l'ancienne ligne
     */
    private void additionCheminEntreLigne(List<Bloc> blocs, List<Integer> positionOuvertures, List<Integer> ouvertures) {
        //max & min des positionD'ouvertures -> ouvertures de la nouvelle ligne
        Integer minPositionOuverture = positionOuvertures.stream().min(Integer::compare).orElse(null);
        Integer maxPositionOuverture = positionOuvertures.stream().max(Integer::compare).orElse(null);

        //max & min des ouvertures -> ouvertures de l'ancienne ligne
        Integer minOuverture = ouvertures.stream().min(Integer::compare).orElse(null);
        Integer maxOuverture = ouvertures.stream().max(Integer::compare).orElse(null);

        //addition sur la valeur min
        if(minOuverture < minPositionOuverture){
            IntStream.range(minOuverture, minPositionOuverture).forEach(value -> blocs.set(value, new Bloc(TypeBloc.DECOR, false)));
         } else if(minOuverture > minPositionOuverture &&
                minOuverture-1 > minPositionOuverture){
            IntStream.range(minPositionOuverture+1, minOuverture+1).forEach(value -> blocs.set(value, new Bloc(TypeBloc.DECOR, false)));
        }

        //addition sur la valeur max
        if(maxOuverture < maxPositionOuverture){
             IntStream.range(maxOuverture, maxPositionOuverture).forEach(value -> blocs.set(value, new Bloc(TypeBloc.DECOR, false)));
        } else if(maxOuverture > maxPositionOuverture &&
             maxOuverture-1 > maxPositionOuverture){
             IntStream.range(maxPositionOuverture+1, maxOuverture+1).forEach(value -> blocs.set(value, new Bloc(TypeBloc.DECOR, false)));
        }

    }

    /**
     * Détermine le nombre d'ouverture
     * @param ligneActuelle la ligne où l'on souhaite déterminer le nombre d'ouverture
     * @return nombreOuverture (valeur entre 1 et 2)
     */
    private int calculNombreOuverture(Ligne ligneActuelle) {
        if(nbLignesAjoute > 5){
            nbOuvertureModifiable = true;
        }

        int nombreOuverture;
        if(nbOuvertureModifiable){
            nbOuvertureModifiable = false;
            nombreOuverture = ThreadLocalRandom.current().nextInt(1,3);
        } else {
            nombreOuverture = ligneActuelle.getOuvertures().size();
        }
        return nombreOuverture;
    }

    private Ligne getLigneActuelle() throws LigneNonCree {
        try {
            return environnement.getLigneActuelle();
        } catch (EnvironnementLigneNonRenseignee environnementLigneNonRenseignee) {
            throw new LigneNonCree(environnementLigneNonRenseignee);
        }
    }

    private void creationChemin(List<Bloc> blocs, List<Integer> positionOuvertures) {
        Integer minPositionOuverture = positionOuvertures.stream().min(Integer::compare).orElse(null);
        Integer maxPositionOuverture = positionOuvertures.stream().max(Integer::compare).orElse(null);

        LoggerCalculEnvironnement.log(LOGGER, Level.FINEST, "Création de chemin entre %s et %s ", minPositionOuverture, maxPositionOuverture);

            if(!minPositionOuverture.equals(maxPositionOuverture)){
                IntStream.range(minPositionOuverture, maxPositionOuverture+1)
                        .forEach(INTCalculEnvironnementUtil.creationBlocChemin(minPositionOuverture, maxPositionOuverture, blocs));
            } else {
                blocs.set(minPositionOuverture, new Bloc(TypeBloc.DECOR, true));
            }
    }

    /**
     * Calcule la ou les position des ouvertures de la nouvelle ligne à être créé.
     * Les positions créées sont unique parmis l'étendue
     * @param nombreOuverture le nombre d'ouvertures à créer, valeur entre 1 et 2
     * @param etendue l'étendue permise pour la création d'ouverture, valeurs entre 0 et {@link Configuration} ENVIRONNEMENT_LARGEUR - 1
     * @return liste des position des cuvertures
     */
    private List<Integer> calculPositionOverturesSuivantes(int nombreOuverture, List<Integer> etendue) {
        List<Integer> positionOuvertures = new ArrayList<>();

        IntStream.range(0, nombreOuverture)
                .forEach(INTCalculEnvironnementUtil.creationPositionUniqueSurEtendue(positionOuvertures, etendue));

        return positionOuvertures;
    }

    /**
     * Calcule l'étendue du paramètre d'ouverture. L'étendue correspond à l'étendue de la liste
     * + l'ajout du delta de distance d'ouverture.
     * Toutefois, les valeurs finales ne peuvent pas être inférieure à 0 ni être
     * suppérieure à {@link Configuration} ENVIRONNEMENT_LARGEUR
     * @param ouvertures la liste d'ouverture sur laquelle nous calculons l'étendue
     * @return l'étendue. valeurs entre 0 et {@link Configuration} ENVIRONNEMENT_LARGEUR - 1
     */
    private List<Integer> calculEtendue(List<Integer> ouvertures) {
        Integer positionDebutAleatoire = ThreadLocalRandom.current().nextInt(Configuration.ENVIRONNEMENT_LARGEUR.getValeur());
        Integer positionDistanceAleatoire = ThreadLocalRandom.current().nextInt(1,
                                Configuration.DISTANCE_DELTA_OUVERTURE.getValeur() + 1);

        Integer minPosition = ouvertures.stream().min(Integer::compare)
                .map(i1 -> i1 - positionDistanceAleatoire)
                .map(INTCalculEnvironnementUtil.SET_MIN_0)
                .orElse(positionDebutAleatoire);

        Integer maxPosition = ouvertures.stream().max(Integer::compare)
                .map(i1 -> i1 + positionDistanceAleatoire)
                .map(INTCalculEnvironnementUtil.setMaxAsLargeur)
                .orElse(positionDebutAleatoire);

        LoggerCalculEnvironnement.log(LOGGER, Level.FINEST, "Calcul de l'étendue terminé : [positionDebutAleatoire : "+positionDebutAleatoire+", " +
                "minPosition : "+minPosition+", maxPosition : "+maxPosition+"] ");

        return Arrays.asList(minPosition, maxPosition);
    }

    /**
     * Permet d'ajouter deux phantom blocs aux extrémités de la liste de blocs
     * @param blocs la liste de blocs sur laquelle on souhaite ajouter les blocs phantom
     */
    private void addPhatomCollisionBlocsAtEnd(List<Bloc> blocs){
        blocs.add(new Bloc(TypeBloc.PHANTOM_OBSTACLE, false));
        blocs.add(0, new Bloc(TypeBloc.PHANTOM_OBSTACLE, false));
    }

    private Ligne removePhatomCollisionBlocsFromLigne(Ligne ligne){
        ligne.getBlocs().removeIf(bloc -> bloc.getTypeBloc().equals(TypeBloc.PHANTOM_OBSTACLE));
        return ligne;
    }

    private List<Bloc> populeBlocs(TypeBloc typeBloc){
        int environnementLargeur = Configuration.ENVIRONNEMENT_LARGEUR.getValeur();
        return IntStream.range(0, environnementLargeur)
                .mapToObj(index -> new Bloc(typeBloc, false))
                .collect(Collectors.toList());
    }

    @Override
    public Environnement recuperationEnvironneement() {
        return environnement;
    }
}
