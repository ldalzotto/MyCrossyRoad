package usecase;

import calcul.INTCalculEnvironnement;
import modele.Ligne;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ldalzotto on 29/03/2017.
 */
public class CustomIntEnvironnementCalcul extends INTCalculEnvironnement {

    @Override
    protected List<Integer> calculPositionOverturesSuivantes(int nombreOuverture, List<Integer> etendue) {
        return Arrays.asList(2);
    }

    public void ajoutLigneOnEnvironnement(Ligne ligne){
        this.recuperationEnvironneement().ajoutLigne(ligne);
    }


}
