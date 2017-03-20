package common.enumeration;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ldalzotto on 20/03/2017.
 */
public class TypeLigneTest {

    @Test
    public void getMenace() throws Exception {
        TypeLigne typeLigne = TypeLigne.ARBRE;
        int menace = typeLigne.getMenace();
        Assert.assertTrue(menace == 0);
    }

    @Test
    public void getTypeAleatoire() throws Exception {
        TypeLigne typeLigne = TypeLigne.getTypeAleatoire(1);
        Assert.assertTrue(typeLigne != null);
        Assert.assertTrue(typeLigne.getMenace() == 1);
    }

}