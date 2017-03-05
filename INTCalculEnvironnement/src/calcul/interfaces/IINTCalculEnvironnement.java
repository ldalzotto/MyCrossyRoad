package calcul.interfaces;

import exception.LigneNonCree;
import modele.Environnement;
import modele.Ligne;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public interface IINTCalculEnvironnement {
    public void initialisationEnvironnement();
    public Ligne creationLigne() throws LigneNonCree;

    /**
     * Permet de récupérer l'environnement actuel
     * @return le {@link Environnement} actuel
     */
    public Environnement recuperationEnvironneement();
}
