package internal.environnement.manager;

import enumeration.EnvironnementInit;
import internal.environnement.manager.exception.EnvironnementNonAffichable;
import internal.environnement.manager.interfaceclass.manager.IINTEnvironnementManager;
import internal.environnement.manager.modele.LigneAffichage;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by ldalzotto on 28/03/2017.
 */
public class INTEnvironnementManagerTest {
    @Test
    public void getInstance() throws Exception {
        Assert.assertTrue(INTEnvironnementManager.getInstance().equals(INTEnvironnementManager.getInstance()));
    }

    @Test
    public void getEnvironnementLignesPourAffichage() throws Exception {
        IINTEnvironnementManager intEnvironnementManager = INTEnvironnementManager.getInstance();
        Stream<LigneAffichage> ligneAffichageStream = intEnvironnementManager.getEnvironnementLignesPourAffichage();

        List<LigneAffichage> ligneAffichages = ligneAffichageStream.collect(Collectors.toList());

        //vérification uniquement sur le type de ligne et la taille
        IntStream.range(0, EnvironnementInit.values().length)
                .forEach(value -> {
                    Assert.assertTrue(EnvironnementInit.values()[value].getLigne().getTypeLigne()
                            .equals(ligneAffichages.get(value).getTypeLigne()));
                });

    }

    @Test
    public void creationLigne() throws Exception {
        LigneAffichage ligneAffichage = INTEnvironnementManager.getInstance().creationLigne();

        //une création de ligne normale
        Assert.assertTrue(ligneAffichage != null);
        Assert.assertTrue(ligneAffichage.getTypeLigne() != null);
        Assert.assertTrue(ligneAffichage.getBlocList() != null);
        Assert.assertTrue(ligneAffichage.getBlocList().size() > 0);

    }

    @Test
    public void getSpawnJoueurPosition() throws Exception {
        IINTEnvironnementManager intEnvironnementManager = INTEnvironnementManager.getInstance();
        Integer positionJoueur = intEnvironnementManager.getSpawnJoueurPosition();

        List<Integer> indexOuvertures = intEnvironnementManager.getLigneOuvertureIndexDepuisIndex(0);

        Assert.assertTrue(indexOuvertures.contains(positionJoueur));
    }

    @Test
    public void getLigneOuvertureIndexDepuisIndex() throws Exception {
        IINTEnvironnementManager intEnvironnementManager = INTEnvironnementManager.getInstance();
        intEnvironnementManager.creationLigne();

        List<LigneAffichage> ligneAffichages =
                intEnvironnementManager.getEnvironnementLignesPourAffichage().collect(Collectors.toList());

        //récupération de la dernière ligne et récupération de la ligne des obstacles
        LigneAffichage ligneAffichage = ligneAffichages.get(ligneAffichages.size()-1);

        List<Integer> indexesObserves =  IntStream.range(0, ligneAffichage.getBlocList().size())
                .mapToObj(value -> {
                    if(!ligneAffichage.getBlocList().get(value).isAnObstacle()){
                        return value;
                    }else {
                        return null;
                    }
                }).filter(integer -> integer!=null)
                .collect(Collectors.toList());

        //détermination des positions d'ouvertures

        List<Integer> integers = intEnvironnementManager.getLigneOuvertureIndexDepuisIndex(ligneAffichages.size()-1);

        integers.forEach(integer -> {
            Assert.assertTrue(indexesObserves.contains(integer));
        });
    }

    @Test(expected = EnvironnementNonAffichable.class)
    public void getLigneOuvertureIndexDepuisIndex_erreurAcces() throws Exception {
        IINTEnvironnementManager intEnvironnementManager = INTEnvironnementManager.getInstance();
        intEnvironnementManager.creationLigne();

        intEnvironnementManager.getLigneOuvertureIndexDepuisIndex(-2);

    }

}