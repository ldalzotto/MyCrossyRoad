package calcul;

import common.enumeration.TypeLigne;
import enumeration.TypeBloc;
import exception.EssaiTropNombreuxSurCreationPosition;
import modele.Bloc;
import modele.Environnement;
import modele.Ligne;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 20/03/2017.
 */
public class INTCalculEnvironnementUtilTest {


    @Test
    public void ajoutLigne() throws Exception {
        Environnement environnement = new Environnement();

        Bloc bloc1 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc2 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc3 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc4 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc5 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc6 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc7 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc8 = new Bloc(TypeBloc.Decor, false);

        List<Bloc> blocs1 = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs2 = Arrays.asList(bloc2, bloc3, bloc4, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs3 = Arrays.asList(bloc1, bloc4, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs4 = Arrays.asList(bloc1, bloc6, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs5 = Arrays.asList(bloc1, bloc7, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);

        List<Ligne> lignes = Arrays.asList(new Ligne(TypeLigne.EAU, blocs1),
                new Ligne(TypeLigne.EAU, blocs2),
                new Ligne(TypeLigne.EAU, blocs3),
                new Ligne(TypeLigne.EAU, blocs4),
                new Ligne(TypeLigne.EAU, blocs5));

        lignes.forEach(INTCalculEnvironnementUtil.ajoutLigne(environnement));

        IntStream.range(0, lignes.size())
                .forEach(value -> {
                    Assert.assertTrue(lignes.get(value).equals(lignes.get(value)));
                });
    }

    @Test
    public void ajoutLigne_NonRenseignee() throws Exception {
        Environnement environnement = new Environnement();
        List<Ligne> lignes = Arrays.asList(null, null);
        lignes.forEach(INTCalculEnvironnementUtil.ajoutLigne(environnement));

        Assert.assertTrue(environnement.getLignesCurseur() == 0);
    }

    @Test
    public void creationBlocChemin() throws Exception {
        int minPositionOuverture = 2;
        int maxPositionOuverture = 5;

        Bloc bloc1 = new Bloc(TypeBloc.Obstacle, false);
        Bloc bloc2 = new Bloc(TypeBloc.Obstacle, false);
        Bloc bloc3 = new Bloc(TypeBloc.Obstacle, false);
        Bloc bloc4 = new Bloc(TypeBloc.Obstacle, false);
        Bloc bloc5 = new Bloc(TypeBloc.Obstacle, false);
        Bloc bloc6 = new Bloc(TypeBloc.Obstacle, false);
        Bloc bloc7 = new Bloc(TypeBloc.Obstacle, false);
        Bloc bloc8 = new Bloc(TypeBloc.Obstacle, false);

        List<Bloc> blocs1 = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);

        IntStream.range(minPositionOuverture,maxPositionOuverture+1)
                .forEach(INTCalculEnvironnementUtil.creationBlocChemin(minPositionOuverture, maxPositionOuverture,
                        blocs1));

        Assert.assertTrue(blocs1.get(minPositionOuverture).getIsOuverture());
        Assert.assertTrue(blocs1.get(minPositionOuverture).getTypeBloc().equals(TypeBloc.Decor));

        Assert.assertTrue(blocs1.get(4).getTypeBloc().equals(TypeBloc.Decor));

        Assert.assertTrue(blocs1.get(maxPositionOuverture).getIsOuverture());
        Assert.assertTrue(blocs1.get(maxPositionOuverture).getTypeBloc().equals(TypeBloc.Decor));
    }

    @Test
    public void creationPositionUniqueSurEtendue() throws Exception {

        List<Integer> positionDesOuvertures = new ArrayList<>();
        List<Integer> etendue = Arrays.asList(3, 8);

        IntStream.range(0,1).forEach(INTCalculEnvironnementUtil.creationPositionUniqueSurEtendue(positionDesOuvertures,
                etendue));

        Assert.assertTrue(positionDesOuvertures.size() == 1);
        Assert.assertTrue(positionDesOuvertures.get(0) >= etendue.get(0));
        Assert.assertTrue(positionDesOuvertures.get(0) <= etendue.get(1));

    }

    @Test
    public void creationPositionUniqueSurEtendue_nbEssai() throws Exception {
        List<Integer> positionDesOuvertures = Arrays.asList(3);
        List<Integer> etendue = Arrays.asList(3, 3);

        try {
            IntStream.range(0,1).forEach(INTCalculEnvironnementUtil.creationPositionUniqueSurEtendue(positionDesOuvertures,
                    etendue));
            Assert.assertFalse(true);
        } catch (EssaiTropNombreuxSurCreationPosition essaiTropNombreuxSurCreationPosition) {
            Assert.assertTrue(true);
        }

    }

}