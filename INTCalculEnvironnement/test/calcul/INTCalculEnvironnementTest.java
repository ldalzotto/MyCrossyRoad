package calcul;

import enumeration.EnvironnementInit;
import modele.Ligne;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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

}