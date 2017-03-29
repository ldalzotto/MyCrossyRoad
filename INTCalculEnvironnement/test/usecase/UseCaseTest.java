package usecase;

import calcul.INTCalculEnvironnement;
import common.enumeration.TypeLigne;
import enumeration.TypeBloc;
import modele.Bloc;
import modele.Ligne;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 29/03/2017.
 */
public class UseCaseTest {

    @Test
    public void creationCheminEntreLigne_casParticulier() throws Exception {

        CustomIntEnvironnementCalcul intCalculEnvironnement = new CustomIntEnvironnementCalcul();
        intCalculEnvironnement.ajoutLigneOnEnvironnement(creationLigne1());

        Ligne ligneApres = intCalculEnvironnement.creationLigne();

        Ligne lignePrecedente = intCalculEnvironnement.recuperationEnvironneement().getLignes().get(
                intCalculEnvironnement.recuperationEnvironneement().getLignesCurseur()-2);

        Ligne ligneActuelle = intCalculEnvironnement.recuperationEnvironneement().getLigneActuelle();

        Integer indexMin = Integer.min(lignePrecedente.getOuverturesIndex().get(0), ligneApres.getOuverturesIndex().get(0));
        Integer indexMax = Integer.max(lignePrecedente.getOuverturesIndex().get(0), ligneApres.getOuverturesIndex().get(0));
        //valeurs minimum
        IntStream.range(indexMin, indexMax +1)
                .forEach(value -> {
                    System.out.println("Asserting index "+value+". LigneActuelle : " + ligneActuelle + ". Ligne précédente : " + lignePrecedente );
                    Assert.assertTrue(ligneActuelle.getBlocs().get(value).getTypeBloc().equals(TypeBloc.DECOR));
                });

    }

    private Ligne creationLigne1(){
        List<Bloc> blocs = Arrays.asList(
                new Bloc(TypeBloc.PHANTOM_OBSTACLE, false),
                new Bloc(TypeBloc.DECOR, false),
                new Bloc(TypeBloc.DECOR, false),
                new Bloc(TypeBloc.DECOR, false),
                new Bloc(TypeBloc.DECOR, true),
                new Bloc(TypeBloc.DECOR, false),
                new Bloc(TypeBloc.DECOR, false),
                new Bloc(TypeBloc.DECOR, false),
                new Bloc(TypeBloc.DECOR, false),
                new Bloc(TypeBloc.DECOR, false),
                new Bloc(TypeBloc.DECOR, false),
                new Bloc(TypeBloc.PHANTOM_OBSTACLE, false)
        );

        return new Ligne(TypeLigne.ROUTE, blocs);
    }

}
