package modele;

import enumeration.TypeBloc;
import enumeration.TypeLigne;
import exception.LigneNonRenseignee;
import exception.MalformedLineException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 20/02/2017.
 */
public class EnvironnementTest {

    @Test
    public void ajoutLigne_TEST(){
        Environnement environnement = new Environnement();

        Bloc bloc1 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc2 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc3 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc4 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc5 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc6 = new Bloc(TypeBloc.Decor, false);

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6);

        Ligne ligne = null;
        try {
            ligne = new Ligne(0, TypeLigne.Arbre, blocs);
        } catch (MalformedLineException e) {
            e.printStackTrace();
            Assert.assertFalse(true);
        }


        try {
            environnement.ajoutLigne(ligne);
        } catch (LigneNonRenseignee ligneNonRenseignee) {
            ligneNonRenseignee.printStackTrace();
            Assert.assertFalse(true);
        }

        try {
            environnement.ajoutLigne(null);
            Assert.assertFalse(true);
        } catch (LigneNonRenseignee ligneNonRenseignee) {
            ligneNonRenseignee.printStackTrace();
        }

        IntStream.range(1, 51).forEach(value -> {
            try {
                environnement.ajoutLigne(new Ligne(0, TypeLigne.Arbre, blocs));
            } catch (LigneNonRenseignee ligneNonRenseignee) {
                ligneNonRenseignee.printStackTrace();
            } catch (MalformedLineException e) {
                e.printStackTrace();
            }
        });

        if(environnement.get_lignesCurseur() != 1){
            Assert.assertFalse(true);
        }

        IntStream.range(1, 51).forEach(value -> {
            try {
                environnement.ajoutLigne(new Ligne(0, TypeLigne.Arbre, blocs));
            } catch (LigneNonRenseignee ligneNonRenseignee) {
                ligneNonRenseignee.printStackTrace();
            } catch (MalformedLineException e) {
                e.printStackTrace();
            }
        });

        if(environnement.get_lignes().size() != 50){
            Assert.assertFalse(true);
        }

    }

}
