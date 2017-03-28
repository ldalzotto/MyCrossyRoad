package internal.environnement.manager.modele;

import common.enumeration.TypeLigne;
import internal.environnement.manager.enumeration.TypeBlocAffichage;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 28/03/2017.
 */
public class LigneAffichageTest {
    @Test
    public void getTypeLigne() throws Exception {
        BlocAffichage blocAffichage = new BlocAffichage(TypeBlocAffichage.PHANTOM_OBSTACLE, false);
        BlocAffichage blocAffichage1 = new BlocAffichage(TypeBlocAffichage.DECOR, true);

        List<BlocAffichage> blocAffichages = Arrays.asList(blocAffichage, blocAffichage1);

        LigneAffichage ligneAffichage = new LigneAffichage(TypeLigne.ROUTE, blocAffichages);

        List<BlocAffichage> blocAffichagesRetour = ligneAffichage.getBlocList();

        IntStream.range(0, blocAffichagesRetour.size())
                .forEach(value -> {
                    Assert.assertTrue(blocAffichagesRetour.get(value).equals(blocAffichages.get(value)));
                });
    }

    @Test
    public void getBlocList() throws Exception {
        BlocAffichage blocAffichage = new BlocAffichage(TypeBlocAffichage.PHANTOM_OBSTACLE, false);
        BlocAffichage blocAffichage1 = new BlocAffichage(TypeBlocAffichage.DECOR, true);

        List<BlocAffichage> blocAffichages = Arrays.asList(blocAffichage, blocAffichage1);

        LigneAffichage ligneAffichage = new LigneAffichage(TypeLigne.ROUTE, blocAffichages);

        Assert.assertTrue(ligneAffichage.getTypeLigne().equals(TypeLigne.ROUTE));

    }

}