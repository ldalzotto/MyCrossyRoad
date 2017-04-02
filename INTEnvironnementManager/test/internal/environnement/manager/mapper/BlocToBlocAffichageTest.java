package internal.environnement.manager.mapper;

import enumeration.TypeBloc;
import internal.environnement.manager.modele.BlocAffichage;
import modele.Bloc;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ldalzotto on 28/03/2017.
 */
public class BlocToBlocAffichageTest {
    @Test
    public void getInstance() throws Exception {
        Assert.assertTrue(BlocToBlocAffichage.getInstance().equals(BlocToBlocAffichage.getInstance()));
    }

    @Test
    public void apply() throws Exception {
        BlocToBlocAffichage blocToBlocAffichage = BlocToBlocAffichage.getInstance();
        Bloc bloc = new Bloc(TypeBloc.OBSTACLE, false);

        BlocAffichage blocAffichageResultat = blocToBlocAffichage.apply(bloc);

        Assert.assertTrue(blocAffichageResultat.isAnObstacle());
        Assert.assertTrue(!blocAffichageResultat.isAPhantomObstacle());
    }

}