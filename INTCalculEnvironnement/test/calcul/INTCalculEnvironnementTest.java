package calcul;

import enumeration.EnvironnementInit;
import enumeration.TypeBloc;
import exception.LigneNonCree;
import modele.Ligne;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 20/03/2017.
 */
public class INTCalculEnvironnementTest {
    @Test
    public void initialisationEnvironnement() throws Exception {
        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();

        List<Ligne> lignes = intCalculEnvironnement.recuperationEnvironneement().getLignes();

        Assert.assertTrue(lignes.size() == EnvironnementInit.values().length);
    }

    @Test
    public void creationLigne() throws Exception {
        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();
        intCalculEnvironnement.creationLigne();

        List<Ligne> lignes = intCalculEnvironnement.recuperationEnvironneement().getLignes();

        Assert.assertTrue(lignes.size() == EnvironnementInit.values().length + 1);
    }

    @Test
    public void creationLigne_changementDuNombreDouverture() throws Exception {

        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();
        intCalculEnvironnement.creationLigne();

        Ligne ligneAvantChangement = intCalculEnvironnement.recuperationEnvironneement().getLigneActuelle();
        Ligne ligneApresChangement;

        do {
            intCalculEnvironnement.creationLigne();
            ligneApresChangement = intCalculEnvironnement.recuperationEnvironneement().getLigneActuelle();
        }while (ligneApresChangement.getOuverturesIndex().size() ==
                ligneAvantChangement.getOuverturesIndex().size());

        Assert.assertTrue(true);
    }

    @Test
    public void creationLigne_sansEnvironnement() throws Exception {
        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        try {
            intCalculEnvironnement.creationLigne();
            Assert.assertFalse(true);
        } catch (LigneNonCree ligneNonCree) {
            Assert.assertTrue(true);
        }
    }

}