package internal.environnement.manager.interfaceclass.manager;

import internal.environnement.manager.exception.EnvironnementNonAffichable;
import internal.environnement.manager.modele.LigneAffichage;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public interface IINTEnvironnementManager {
    public Stream<LigneAffichage> getEnvironnementLignesPourAffichage();
    public List<Integer> getLigneOuvertureIndexDepuisIndex(Integer index) throws EnvironnementNonAffichable;
    public LigneAffichage creationLigne() ;
    public Integer getSpawnJoueurPosition() throws EnvironnementNonAffichable;
}
