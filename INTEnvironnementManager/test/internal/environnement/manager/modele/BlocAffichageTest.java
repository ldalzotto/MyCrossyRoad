package internal.environnement.manager.modele;

import internal.environnement.manager.enumeration.TypeBlocAffichage;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 28/03/2017.
 */
public class BlocAffichageTest {
    @Test
    public void isAnObstacle() throws Exception {
        BlocAffichage blocAffichage = new BlocAffichage(TypeBlocAffichage.OBSTACLE, false);
        Assert.assertTrue(blocAffichage.isAnObstacle());
    }

    @Test
    public void isNotAnObstacle() throws Exception {
        BlocAffichage blocAffichage = new BlocAffichage(TypeBlocAffichage.OBSTACLE, true);
        Assert.assertTrue(!blocAffichage.isAnObstacle());

        BlocAffichage blocAffichage1 = new BlocAffichage(TypeBlocAffichage.DECOR, false);
        Assert.assertTrue(!blocAffichage1.isAnObstacle());

    }

    @Test
    public void isAPhantomObstacle() throws Exception {
        BlocAffichage blocAffichage = new BlocAffichage(TypeBlocAffichage.PHANTOM_OBSTACLE, true);
        Assert.assertTrue(blocAffichage.isAPhantomObstacle());
    }

    @Test
    public void isNotAPhantomObstacle() throws Exception {
        BlocAffichage blocAffichage = new BlocAffichage(TypeBlocAffichage.DECOR, true);
        Assert.assertTrue(!blocAffichage.isAPhantomObstacle());
    }

}