package calcul;

import common.enumeration.TypeLigne;
import enumeration.TypeBloc;
import exception.LigneNonRenseignee;
import modele.Bloc;
import modele.Environnement;
import modele.Ligne;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 20/03/2017.
 */
public class INTCalculEnvironnementUtilTest {
    @Test
    public void AJOUT_LIGNE() throws Exception {
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

        lignes.forEach(INTCalculEnvironnementUtil.AJOUT_LIGNE(environnement));

        IntStream.range(0, lignes.size())
                .forEach(value -> {
                    Assert.assertTrue(lignes.get(value).equals(lignes.get(value)));
                });

    }

    @Test
    public void CREATION_BLOC_CHEMIN() throws Exception {

    }

    @Test
    public void CREATION_POSITION_UNIQUE_SUR_ETENDUE() throws Exception {

    }

}