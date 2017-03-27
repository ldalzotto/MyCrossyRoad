package calcul;

import enumeration.EnvironnementInit;
import enumeration.TypeBloc;
import exception.LigneNonCree;
import modele.Ligne;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

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
    public void initialisationEnvironnement_phatomObstacle() throws Exception {
        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();

        List<Ligne> lignes = intCalculEnvironnement.recuperationEnvironneement().getLignes();

        Assert.assertTrue(lignes.get(1).getBlocs().get(0).getTypeBloc().equals(TypeBloc.PhantomObstacle));

    }

    @Test
    public void creationLigne() throws Exception {
        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();
        intCalculEnvironnement.creationLigne();

        List<Ligne> lignes = intCalculEnvironnement.recuperationEnvironneement().getLignes();

        Assert.assertTrue(lignes.size() == EnvironnementInit.values().length + 1);
    }

    @Test
    public void creationLigne_changementDuNombreDouverture() throws Exception {

        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();
        intCalculEnvironnement.creationLigne();

        Ligne ligneAvantChangement = intCalculEnvironnement.recuperationEnvironneement().getLigneActuelle();
        Ligne ligneApresChangement;

        do {
            intCalculEnvironnement.creationLigne();
            ligneApresChangement = intCalculEnvironnement.recuperationEnvironneement().getLigneActuelle();
        }while (ligneApresChangement.getOuverturesIndex().size() ==
                ligneAvantChangement.getOuverturesIndex().size());

        Assert.assertTrue(true);
    }

    @Test
    public void creationLigne_uniqueOuverture() throws Exception {

        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();
        intCalculEnvironnement.creationLigne();

        Ligne ligne;

        do {
            ligne = intCalculEnvironnement.recuperationEnvironneement().getLigneActuelle();
            intCalculEnvironnement.creationLigne();
        } while (ligne.getOuvertures().size() != 1);

        Assert.assertTrue(true);

    }

    @Test
    public void creationLigne_creationChemin() throws Exception {

        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();
        intCalculEnvironnement.creationLigne();

        Ligne ligne;

        do {
            intCalculEnvironnement.creationLigne();
            ligne = intCalculEnvironnement.recuperationEnvironneement().getLigneActuelle();
        }while (ligne.getOuverturesIndex().size() <= 1);

        Ligne ligne1 = intCalculEnvironnement.recuperationEnvironneement().getLigneActuelle();

        List<Integer> integers = ligne.getOuverturesIndex();
        integers.forEach(integer -> {
            Assert.assertTrue(ligne1.getBlocs().get(integer).getIsOuverture());
        });
        Assert.assertTrue(integers.size() == 2);

        //on remplit avec des blocs de types décors
        IntStream.range(integers.get(0), integers.get(1))
            .forEach(value -> {
                Assert.assertTrue(ligne1.getBlocs().get(value).getTypeBloc().equals(TypeBloc.Decor));
            });

    }

    @Test
    public void creationLigne_lienCheminEntreLigne() throws Exception {
        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();


        //TODO lors de la création de la ligne, les phantom blocs initialisés disparaissent
        Ligne ligneAvant = intCalculEnvironnement.creationLigne();
        Ligne ligneApres = intCalculEnvironnement.creationLigne();

        while (ligneApres.getOuverturesIndex().equals(ligneAvant.getOuverturesIndex())
                || ligneApres.getOuverturesIndex().contains(ligneAvant.getOuverturesIndex().get(0))) {
            ligneAvant = ligneApres;
            ligneApres = intCalculEnvironnement.creationLigne();
        }

        Ligne lignePrecedente = intCalculEnvironnement.recuperationEnvironneement().getLignes().get(
                intCalculEnvironnement.recuperationEnvironneement().getLignesCurseur()-2);

        Ligne ligneActuelle = intCalculEnvironnement.recuperationEnvironneement().getLigneActuelle();

        Integer indexMin = Integer.min(lignePrecedente.getOuverturesIndex().get(0), ligneApres.getOuverturesIndex().get(0));
        Integer indexMax = Integer.max(lignePrecedente.getOuverturesIndex().get(0), ligneApres.getOuverturesIndex().get(0));
        //valeurs minimum
        IntStream.range(indexMin, indexMax +1)
                .forEach(value -> {
                    System.out.println("Asserting index "+value+". LigneActuelle : " + ligneActuelle + ". Ligne précédente : " + lignePrecedente );
                    Assert.assertTrue(ligneActuelle.getBlocs().get(value).getTypeBloc().equals(TypeBloc.Decor));
                });

    }

    @Test
    public void creationLigne_phantomObstacle() throws Exception {

        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();
        intCalculEnvironnement.creationLigne();

        Ligne ligne = intCalculEnvironnement.recuperationEnvironneement().getLigneActuelle();

        Assert.assertTrue(ligne.getBlocs().get(0).getTypeBloc().equals(TypeBloc.PhantomObstacle));
        Assert.assertTrue(ligne.getBlocs().get(ligne.getBlocs().size()-1).getTypeBloc().equals(TypeBloc.PhantomObstacle));
    }

    @Test
    public void creationLigne_sansEnvironnement() throws Exception {
        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        try {
            intCalculEnvironnement.creationLigne();
            Assert.assertFalse(true);
        } catch (LigneNonCree ligneNonCree) {
            Assert.assertTrue(true);
        }
    }

}