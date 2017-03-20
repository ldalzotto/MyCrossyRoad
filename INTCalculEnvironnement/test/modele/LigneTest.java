package modele;

import common.enumeration.TypeLigne;
import enumeration.TypeBloc;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 20/03/2017.
 */
public class LigneTest {
    @Test
    public void getOuvertures() throws Exception {

        Bloc bloc1 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc2 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc3 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc4 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc5 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc6 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc7 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc8 = new Bloc(TypeBloc.Decor, false);

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> awaitedOuvertureBloc = Arrays.asList(bloc2, bloc4, bloc7);

        Ligne ligne = new Ligne(TypeLigne.EAU, blocs);

        List<Bloc> blocList = ligne.getOuvertures();

        Assert.assertTrue(blocList.size() == 3);
        blocList.forEach(bloc -> {
            Assert.assertTrue(awaitedOuvertureBloc.contains(bloc));
        });

    }

    @Test
    public void getOuverturesIndex() throws Exception {
        Bloc bloc1 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc2 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc3 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc4 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc5 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc6 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc7 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc8 = new Bloc(TypeBloc.Decor, false);

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Integer> awaitedIndex = Arrays.asList(1, 3, 6);

        Ligne ligne = new Ligne(TypeLigne.EAU, blocs);

        List<Integer> integerList = ligne.getOuverturesIndex();

        Assert.assertTrue(integerList.size() == 3);
        integerList.forEach(bloc -> {
            Assert.assertTrue(awaitedIndex.contains(bloc));
        });
    }

    @Test
    public void getTypeLigne() throws Exception {
        Bloc bloc1 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc2 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc3 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc4 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc5 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc6 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc7 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc8 = new Bloc(TypeBloc.Decor, false);

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Integer> awaitedIndex = Arrays.asList(1, 3, 6);

        Ligne ligne = new Ligne(TypeLigne.EAU, blocs);

        Assert.assertTrue(ligne.getTypeLigne().equals(TypeLigne.EAU));

    }

    @Test
    public void getBlocs() throws Exception {
        Bloc bloc1 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc2 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc3 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc4 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc5 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc6 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc7 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc8 = new Bloc(TypeBloc.Decor, false);

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Integer> awaitedIndex = Arrays.asList(1, 3, 6);

        Ligne ligne = new Ligne(TypeLigne.EAU, blocs);

        List<Bloc> blocs1 = ligne.getBlocs();

        IntStream.range(0, blocs1.size())
                .forEach(value -> {
                    Assert.assertTrue(blocs1.get(value).equals(blocs.get(value)));
                });

    }

}