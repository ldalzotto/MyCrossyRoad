package calcul;

import calcul.interfaces.IINTCalculEnvironnement;
import enumeration.Configuration;
import enumeration.EnvironnementInit;
import enumeration.TypeBloc;
import enumeration.TypeLigne;
import exception.EnvironnementLigneNonRenseignee;
import exception.LigneNonCree;
import exception.PositionNonCree;
import modele.Bloc;
import modele.Environnement;
import modele.Ligne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class INTCalculEnvironnement implements IINTCalculEnvironnement {

    private Environnement _environnement;
    private boolean _nbOuvertureModifiable;
    private int _nbLignesAjoute;

    public INTCalculEnvironnement(){
        _environnement = new Environnement();
        _nbOuvertureModifiable = true;
        _nbLignesAjoute = 0;
    }

    @Override
    public void initialisationEnvironnement() {
            IntStream.range(0, EnvironnementInit.values().length)
                    .mapToObj(index -> EnvironnementInit.values()[index].get_ligne())
                    .forEach(INTCalculEnvironnementUtil.AJOUT_LIGNE(_environnement));
    }

    @Override
    public Ligne creationLigne() throws LigneNonCree{
        int menace = ThreadLocalRandom.current().nextInt(0,2);

        //type de ligne
        TypeLigne typeLigne = TypeLigne.getTypeAleatoire(menace);

        //popule bloc
        List<Bloc> blocs = new ArrayList<>();
        if(menace == 0){
            blocs = populeBlocs(TypeBloc.Obstacle);
        }else if (menace == 1){
            blocs = populeBlocs(TypeBloc.Decor);
        }

        try {
            Ligne ligneActuelle = removePhatomCollisionBlocsFromLigne(getLigneActuelle());

            //détermine nombre d'ouverture pour la ligne suivante
            int nombreOuverture = calculNombreOuverture(ligneActuelle);
            System.out.println("Nombre d'ouverture calculées : " + nombreOuverture);

            //détermine position d'ouverture de la ligne actuelle
            List<Integer> ouvertures = ligneActuelle.getOuverturesIndex();
            System.out.println("Position des ouvertures de la dernière ligne insérée : " + ouvertures);

            //calcul de l'étendue des positions possibles
            System.out.println("Calcul de l'étendue ...");
            List<Integer> etendue = calculEtendue(ouvertures);
            System.out.println("Calcul de l'étendue, END. Etendue : " + etendue.toString());

            //calcl position des ouvertures suivantes
            List<Integer> positionOuvertures = calculPositionOverturesSuivantes(nombreOuverture, etendue);
            System.out.println("Positions calculées : " + positionOuvertures);

            //création des blocs
            creationChemin(blocs, positionOuvertures);

            //relier la ligne à créer avec la précedente si nécessaire
            additionCheminEntreLigne(blocs, positionOuvertures, ouvertures);

            //ajouter des blocs de collision invisible aux extrémités de la ligne
            addPhatomCollisionBlocsAtEnd(blocs);

            Ligne ligne = new Ligne(menace, typeLigne, blocs);
            Integer positionLigne =  _environnement.ajoutLigne(ligne);
            System.out.println("Ligne ajoutée sur la position " + positionLigne);
            _nbLignesAjoute++;
            return ligne;
        } catch (Exception e) {
            e.printStackTrace();
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
        Integer minPositionOuverture = positionOuvertures.stream().min((o1, o2) -> Integer.compare(o1, o2)).get();
        Integer maxPositionOuverture = positionOuvertures.stream().max((o1, o2) -> Integer.compare(o1, o2)).get();

        //max & min des ouvertures -> ouvertures de l'ancienne ligne
        Integer minOuverture = ouvertures.stream().min(Integer::compare).get();
        Integer maxOuverture = ouvertures.stream().max(Integer::compare).get();

        List<Bloc> blocCopy = blocs;

        //addition sur la valeur min
        if(minOuverture < minPositionOuverture){
            IntStream.range(minOuverture, minPositionOuverture-1).forEach(value -> blocCopy.set(value, new Bloc(TypeBloc.Decor, false)));
        } else if(minOuverture > minPositionOuverture) {
            if(minOuverture-1 > minPositionOuverture){
                IntStream.range(minPositionOuverture+1, minOuverture+1).forEach(value -> blocCopy.set(value, new Bloc(TypeBloc.Decor, false)));
            }
        }

        //addition sur la valeur max
        if(maxOuverture < maxPositionOuverture){
            IntStream.range(maxOuverture, maxPositionOuverture-1).forEach(value -> blocCopy.set(value, new Bloc(TypeBloc.Decor, false)));
        } else if(maxOuverture > maxPositionOuverture) {
            if(maxOuverture-1 > maxPositionOuverture){
                IntStream.range(maxPositionOuverture+1, maxOuverture+1).forEach(value -> blocCopy.set(value, new Bloc(TypeBloc.Decor, false)));
            }
        }

    }

    /**
     * Détermine le nombre d'ouverture
     * @param ligneActuelle
     * @return nombreOuverture (valeur entre 1 et 2)
     */
    private int calculNombreOuverture(Ligne ligneActuelle) {
        if(_nbLignesAjoute > 5){
            _nbOuvertureModifiable = true;
        }

        int nombreOuverture;
        if(_nbOuvertureModifiable){
            _nbOuvertureModifiable = false;
            nombreOuverture = ThreadLocalRandom.current().nextInt(1,3);
        } else {
            nombreOuverture = ligneActuelle.getOuvertures().size();
        }
        return nombreOuverture;
    }

    private Ligne getLigneActuelle() throws LigneNonCree {
        Ligne ligneActuelle = null;
        try {
            ligneActuelle = _environnement.getLigneActuelle();
        } catch (EnvironnementLigneNonRenseignee environnementLigneNonRenseignee) {
            environnementLigneNonRenseignee.printStackTrace();
            throw new LigneNonCree(environnementLigneNonRenseignee);
        }
        return ligneActuelle;
    }

    private void creationChemin(List<Bloc> blocs, List<Integer> positionOuvertures) {
        Optional<Integer> oMinPositionOuverture = positionOuvertures.stream().min(Integer::compare);
        Optional<Integer> oMaxPositionOuverture = positionOuvertures.stream().max(Integer::compare);

        Integer minPositionOuverture;
        Integer maxPositionOuverture;

        if(oMinPositionOuverture.isPresent()){
            minPositionOuverture = oMinPositionOuverture.get();
        } else {
            minPositionOuverture = null;
        }
        if(oMaxPositionOuverture.isPresent()){
            maxPositionOuverture = oMaxPositionOuverture.get();
        } else {
            maxPositionOuverture = null;
        }

        System.out.println("Création de chemin entre "+minPositionOuverture+" et "+maxPositionOuverture+"");
        List<Bloc> blocCopy = blocs;
        if(!minPositionOuverture.equals(maxPositionOuverture)){

            IntStream.range(minPositionOuverture, maxPositionOuverture+1)
                    .forEach(INTCalculEnvironnementUtil.CREATION_BLOC_CHEMIN(minPositionOuverture, maxPositionOuverture, blocCopy));

        } else {
            blocCopy.set(minPositionOuverture, new Bloc(TypeBloc.Decor, true));
        }
    }

    /**
     * Calcule la ou les position des ouvertures de la nouvelle ligne à être créé.
     * Les positions créées sont unique parmis l'étendue
     * @param nombreOuverture le nombre d'ouvertures à créer, valeur entre 1 et 2
     * @param etendue l'étendue permise pour la création d'ouverture, valeurs entre 0 et {@link Configuration} EnvironnementLargeur - 1
     * @return liste des position des cuvertures
     */
    private List<Integer> calculPositionOverturesSuivantes(int nombreOuverture, List<Integer> etendue) throws Exception{
        List<Integer> positionOuvertures = new ArrayList<>();

        try {
            IntStream.range(0, nombreOuverture)
                    .forEach(INTCalculEnvironnementUtil.CREATION_POSITION_UNIQUE_SUR_ETENDUE(positionOuvertures, etendue));
        } catch (RuntimeException e) {
            throw new PositionNonCree("Les positions n'ont pas pu être calculées.", e);
        }

        return positionOuvertures;
    }

    /**
     * Calcule l'étendue du paramètre d'ouverture. L'étendue correspond à l'étendue de la liste
     * + l'ajout du delta de distance d'ouverture.
     * Toutefois, les valeurs finales ne peuvent pas être inférieure à 0 ni être
     * suppérieure à {@link Configuration} EnvironnementLargeur
     * @param ouvertures la liste d'ouverture sur laquelle nous calculons l'étendue
     * @return l'étendue. valeurs entre 0 et {@link Configuration} EnvironnementLargeur - 1
     */
    private List<Integer> calculEtendue(List<Integer> ouvertures) {
        Integer positionDebutAleatoire = ThreadLocalRandom.current().nextInt(Configuration.EnvironnementLargeur.get_valeur());
        Integer positionDistanceAleatoire = ThreadLocalRandom.current().nextInt(1,
                                Configuration.DistanceDeltaOuverture.get_valeur() + 1);

        Integer minPosition = ouvertures.stream().min(Integer::compare)
                .map(i1 -> i1 - positionDistanceAleatoire)
                .map(INTCalculEnvironnementUtil.SET_MIN_0)
                .orElse(positionDebutAleatoire);

        Integer maxPosition = ouvertures.stream().max(Integer::compare)
                .map(i1 -> i1 + positionDistanceAleatoire)
                .map(INTCalculEnvironnementUtil.SET_MAX_AS_LARGEUR)
                .orElse(positionDebutAleatoire);

        System.out.println("Calcul de l'étendue terminé : [positionDebutAleatoire : "+positionDebutAleatoire+", " +
                "minPosition : "+minPosition+", maxPosition : "+maxPosition+"] ");

        return Arrays.asList(minPosition, maxPosition);
    }

    /**
     * Permet d'ajouter deux phantom blocs aux extrémités de la liste de blocs
     * @param blocs la liste de blocs sur laquelle on souhaite ajouter les blocs phantom
     */
    private void addPhatomCollisionBlocsAtEnd(List<Bloc> blocs){
        blocs.add(new Bloc(TypeBloc.PhantomObstacle, false));
        blocs.add(0, new Bloc(TypeBloc.PhantomObstacle, false));
    }

    private Ligne removePhatomCollisionBlocsFromLigne(Ligne ligne){
        ligne.get_blocs().removeIf(bloc -> {
            if(bloc.get_typeBloc().equals(TypeBloc.PhantomObstacle)){
                return true;
            }
            return false;
        });
        return ligne;
    }

    private List<Bloc> populeBlocs(TypeBloc typeBloc){
        int environnementLargeur = Configuration.EnvironnementLargeur.get_valeur();
        return IntStream.range(0, environnementLargeur)
                .mapToObj(index -> new Bloc(typeBloc, false))
                .collect(Collectors.toList());
    }

    @Override
    public Environnement recuperationEnvironneement() {
        return _environnement;
    }
}
