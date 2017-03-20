package INTEnvironnementManager;

import INTEnvironnementManager.exception.EnvironnementNonAffichable;
import INTEnvironnementManager.exception.CreationLigne;
import INTEnvironnementManager.exception.JoueurNonPlace;
import INTEnvironnementManager.interfaceManger.IINTEnvironnementManager;
import INTEnvironnementManager.mapper.BlocToBlocAffichage;
import INTEnvironnementManager.mapper.interfaceMapper.IConverter;
import INTEnvironnementManager.modele.BlocAffichage;
import INTEnvironnementManager.modele.LigneAffichage;
import calcul.INTCalculEnvironnement;
import calcul.interfaces.IINTCalculEnvironnement;
import common.enumeration.TypeLigne;
import exception.ConstructionLigneOrdonnee;
import exception.LigneNonCree;
import modele.Bloc;
import modele.Ligne;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ldalzotto on 25/02/2017.
 */
public class INTEnvironnementManager implements IINTEnvironnementManager{

    private IINTCalculEnvironnement _intCalculEnvironnement;

    private IConverter<Bloc, BlocAffichage> _blocBlocAffichageIConverter = BlocToBlocAffichage.getInstance();

    private static INTEnvironnementManager _instance = null;

    public static IINTEnvironnementManager getInstance() {
        if(_instance == null){
            try {
                _instance = new INTEnvironnementManager();
            } catch (EnvironnementNonAffichable environnementNonAffichable) {
                environnementNonAffichable.printStackTrace();
            }
        }
        return _instance;
    }

    private INTEnvironnementManager() throws EnvironnementNonAffichable{
        _intCalculEnvironnement = new INTCalculEnvironnement();
        _intCalculEnvironnement.initialisationEnvironnement();
    }

    /**
     * Permet de créer une ligne à {@link modele.Environnement} depuis {@link INTEnvironnementManager}
     * @throws LigneNonCree si la ligne n'a pas pu être créée
     */
    public LigneAffichage creationLigne() throws CreationLigne{
        try {
            Ligne ligneCree = _intCalculEnvironnement.creationLigne();
            List<BlocAffichage> blocAffichages = ligneCree.getBlocs().stream().map(bloc -> _blocBlocAffichageIConverter.apply(bloc))
                    .collect(Collectors.toList());
            TypeLigne typeLigneAffichage = ligneCree.getTypeLigne();
            return new LigneAffichage(typeLigneAffichage, blocAffichages, typeLigneAffichage.getMenace());
        } catch (LigneNonCree ligneNonCree) {
            throw new CreationLigne("La ligne n'a pas été créé", ligneNonCree);
        }
    }

    /**
     * Méthode permettant de retourner un stream de ligne propre et exactement représentatif de l'environnement à afficher
     * @throws EnvironnementNonAffichable si l'environneme nt n'est pas affichable
     */
    public Stream<LigneAffichage> getEnvironnementLignesPourAffichage() throws EnvironnementNonAffichable{
        if(_intCalculEnvironnement.recuperationEnvironneement() != null && _intCalculEnvironnement.recuperationEnvironneement().getLignes() != null){
            try {
                return _intCalculEnvironnement.recuperationEnvironneement().getLignesDepuisCurseur()
                        .map(ligne -> {
                            List<BlocAffichage> blocAffichages = ligne.getBlocs().stream().map(bloc -> _blocBlocAffichageIConverter.apply(bloc)).collect(Collectors.toList());
                            TypeLigne typeLigneAffichage = ligne.getTypeLigne();
                            return new LigneAffichage(typeLigneAffichage, blocAffichages, typeLigneAffichage.getMenace());
                        });
            } catch (ConstructionLigneOrdonnee constructionLigneOrdonnee) {
                throw new EnvironnementNonAffichable("L'environnement ne peut pas être affiché", constructionLigneOrdonnee);
            }
        } else {
            throw new EnvironnementNonAffichable("L'environnement ne peut pas être affiché", null);
        }
    }

    public Integer getSpawnJoueurPosition() throws JoueurNonPlace{
        try {
             Optional<Integer> position = getLigneOuvertureIndexDepuisIndex(0).stream().findAny();
            return position.orElseThrow(() -> new JoueurNonPlace("Le joueur n'a pas pu être placé", null));
        } catch (EnvironnementNonAffichable environnementNonAffichable) {
            throw new JoueurNonPlace("Le joueur n'a pas pu être placé", null);
        }
    }

    public List<Integer> getLigneOuvertureIndexDepuisIndex(Integer index) throws EnvironnementNonAffichable{
        try {
            return _intCalculEnvironnement.recuperationEnvironneement().getLignes().get(index).getOuverturesIndex();
        } catch (Exception e) {
            throw new EnvironnementNonAffichable("Une erreur est survenue lors d'une opération sur l'environnement", e);
        }
    }

}
