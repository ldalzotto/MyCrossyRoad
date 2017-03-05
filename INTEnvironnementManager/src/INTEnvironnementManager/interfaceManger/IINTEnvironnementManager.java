package INTEnvironnementManager.interfaceManger;

import INTEnvironnementManager.exception.CreationLigne;
import INTEnvironnementManager.exception.EnvironnementNonAffichable;
import INTEnvironnementManager.exception.JoueurNonPlace;
import INTEnvironnementManager.modele.LigneAffichage;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public interface IINTEnvironnementManager {
    public Stream<LigneAffichage> getEnvironnementLignesPourAffichage() throws EnvironnementNonAffichable;
    public List<Integer> getLigneOuvertureIndexDepuisIndex(Integer index) throws EnvironnementNonAffichable;
    public LigneAffichage creationLigne() throws CreationLigne;
    public Integer getSpawnJoueurPosition() throws JoueurNonPlace;
}
