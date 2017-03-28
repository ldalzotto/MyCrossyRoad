package internal.environnement.manager;

import internal.environnement.manager.exception.EnvironnementNonAffichable;
import internal.environnement.manager.exception.JoueurNonPlace;
import internal.environnement.manager.interfaceclass.manager.IINTEnvironnementManager;
import internal.environnement.manager.mapper.BlocToBlocAffichage;
import internal.environnement.manager.mapper.interfaceMapper.IConverter;
import internal.environnement.manager.modele.BlocAffichage;
import internal.environnement.manager.modele.LigneAffichage;
import calcul.INTCalculEnvironnement;
import calcul.interfaces.IINTCalculEnvironnement;
import common.enumeration.TypeLigne;
import modele.Bloc;
import modele.Ligne;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ldalzotto on 25/02/2017.
 */
public class INTEnvironnementManager implements IINTEnvironnementManager {

    private IINTCalculEnvironnement intCalculEnvironnement;

    private IConverter<Bloc, BlocAffichage> blocBlocAffichageIConverter = BlocToBlocAffichage.getInstance();

    private static INTEnvironnementManager instance = null;

    private INTEnvironnementManager() {
        intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();
    }

    public static IINTEnvironnementManager getInstance() {
        if(instance == null){
            instance = new INTEnvironnementManager();
        }
        return instance;
    }

    /**
     * Permet de créer une ligne à {@link modele.Environnement} depuis {@link INTEnvironnementManager}
     */
    @Override
    public LigneAffichage creationLigne() {
            Ligne ligneCree = intCalculEnvironnement.creationLigne();
            List<BlocAffichage> blocAffichages = ligneCree.getBlocs().stream().map(blocBlocAffichageIConverter::apply)
                    .collect(Collectors.toList());
            TypeLigne typeLigneAffichage = ligneCree.getTypeLigne();
            return new LigneAffichage(typeLigneAffichage, blocAffichages);
    }

    /**
     * Méthode permettant de retourner un stream de ligne propre et exactement représentatif de l'environnement à afficher
     */
    @Override
    public Stream<LigneAffichage> getEnvironnementLignesPourAffichage() {
                return intCalculEnvironnement.recuperationEnvironneement().getLignesDepuisCurseur()
                        .map(ligne -> {
                            List<BlocAffichage> blocAffichages = ligne.getBlocs().stream().map(blocBlocAffichageIConverter::apply)
                                    .collect(Collectors.toList());
                            TypeLigne typeLigneAffichage = ligne.getTypeLigne();
                            return new LigneAffichage(typeLigneAffichage, blocAffichages);
                        });
    }

    @Override
    public Integer getSpawnJoueurPosition() throws JoueurNonPlace{
        try {
             Optional<Integer> position = getLigneOuvertureIndexDepuisIndex(0).stream().findAny();
            return position.orElseThrow(() -> new JoueurNonPlace("Le joueur n'a pas pu être placé", null));
        } catch (EnvironnementNonAffichable environnementNonAffichable) {
            throw new JoueurNonPlace("Le joueur n'a pas pu être placé", environnementNonAffichable);
        }
    }

    @Override
    public List<Integer> getLigneOuvertureIndexDepuisIndex(Integer index) throws EnvironnementNonAffichable{
        try {
            return intCalculEnvironnement.recuperationEnvironneement().getLignes().get(index).getOuverturesIndex();
        } catch (Exception e) {
            throw new EnvironnementNonAffichable("Une erreur est survenue lors d'une opération sur l'environnement", e);
        }
    }

}
