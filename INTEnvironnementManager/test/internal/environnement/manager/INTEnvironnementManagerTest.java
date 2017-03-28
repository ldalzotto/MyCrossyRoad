package internal.environnement.manager;

import enumeration.Configuration;
import enumeration.EnvironnementInit;
import internal.environnement.manager.interfaceclass.manager.IINTEnvironnementManager;
import internal.environnement.manager.mapper.BlocToBlocAffichage;
import internal.environnement.manager.modele.LigneAffichage;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 28/03/2017.
 */
public class INTEnvironnementManagerTest {
    @Test
    public void getInstance() throws Exception {
        Assert.assertTrue(INTEnvironnementManager.getInstance().equals(INTEnvironnementManager.getInstance()));
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
    public void getEnvironnementLignesPourAffichage() throws Exception {
        IINTEnvironnementManager intEnvironnementManager = INTEnvironnementManager.getInstance();
        Stream<LigneAffichage> ligneAffichageStream = intEnvironnementManager.getEnvironnementLignesPourAffichage();

        List<LigneAffichage> ligneAffichages = ligneAffichageStream.collect(Collectors.toList());

        //vérification uniquement sur le type de ligne et la taille
        Assert.assertTrue(ligneAffichages.size() == EnvironnementInit.values().length);
        IntStream.range(0, EnvironnementInit.values().length)
                .forEach(value -> {
                    Assert.assertTrue(EnvironnementInit.values()[value].getLigne().getTypeLigne()
                            .equals(ligneAffichages.get(value).getTypeLigne()));
                });

    }

    @Test
    public void getSpawnJoueurPosition() throws Exception {

    }

    @Test
    public void getLigneOuvertureIndexDepuisIndex() throws Exception {

    }

}