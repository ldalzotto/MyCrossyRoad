package modele;

import common.enumeration.TypeLigne;
import enumeration.Configuration;
import enumeration.TypeBloc;
import exception.ConstructionLigneOrdonnee;
import exception.EnvironnementLigneNonRenseignee;
import exception.LigneNonRenseignee;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 20/03/2017.
 */
public class EnvironnementTest {

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

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);

        Ligne ligneAAjouter = new Ligne(TypeLigne.ROUTE, blocs);

        environnement.ajoutLigne(ligneAAjouter);

        Assert.assertTrue(environnement.getLignes().size() == 1);
        Assert.assertTrue(environnement.getLignesCurseur() == 1);
        Assert.assertTrue(environnement.getLigneActuelle().equals(ligneAAjouter));
    }

    @Test
    public void ajoutLigne_LigneNulle() throws Exception {
        Environnement environnement = new Environnement();
        try {
            environnement.ajoutLigne(null);
            Assert.assertFalse(true);
        } catch (LigneNonRenseignee ligneNonRenseignee) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void ajoutLigne_RetourCurseur() throws Exception {
        Environnement environnement = new Environnement();

        Bloc bloc1 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc2 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc3 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc4 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc5 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc6 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc7 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc8 = new Bloc(TypeBloc.Decor, false);

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);

        Ligne ligneAAjouter = new Ligne(TypeLigne.ROUTE, blocs);

        Integer longueurConfiguration = Configuration.EnvironnementLongueur.get_valeur();
        IntStream.range(0, longueurConfiguration+1)
                .forEach(value -> {
                    try {
                        environnement.ajoutLigne(ligneAAjouter);
                    } catch (LigneNonRenseignee ligneNonRenseignee) {
                        ligneNonRenseignee.printStackTrace();
                    }
                });

        //retour du curseur
        Assert.assertTrue(environnement.getLignesCurseur() == 1);

    }

    @Test
    public void getLigneActuelle() throws Exception {
        Environnement environnement = new Environnement();

        Bloc bloc1 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc2 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc3 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc4 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc5 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc6 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc7 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc8 = new Bloc(TypeBloc.Decor, false);

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);

        Ligne ligneAAjouter = new Ligne(TypeLigne.ROUTE, blocs);

        environnement.ajoutLigne(ligneAAjouter);

        Assert.assertTrue(environnement.getLigneActuelle().equals(ligneAAjouter));
    }

    @Test
    public void getLigneActuelle_ErreurEnvironnement() throws Exception {
        Environnement environnement = new Environnement();

        try {
            environnement.getLigneActuelle();
            Assert.assertFalse(true);
        } catch (EnvironnementLigneNonRenseignee environnementLigneNonRenseignee) {
            Assert.assertTrue(true);
        }

    }

    @Test
    public void getLignesDepuisCurseur() throws Exception {
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
        List<Bloc> blocs6 = Arrays.asList(bloc1, bloc1, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs7 = Arrays.asList(bloc1, bloc2, bloc5, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs8 = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs9 = Arrays.asList(bloc3, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs10 = Arrays.asList(bloc2, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);

        List<List<Bloc>> lists = Arrays.asList(blocs1, blocs2, blocs3, blocs4, blocs5);

        lists.forEach(blocs -> {
            try {
                environnement.ajoutLigne(new Ligne(TypeLigne.ARBRE, blocs));
            } catch (LigneNonRenseignee ligneNonRenseignee) {
                ligneNonRenseignee.printStackTrace();
            }
        });

        Assert.assertTrue(environnement.getLignesDepuisCurseur().count() == 5);
        List<Ligne> lignes = environnement.getLignesDepuisCurseur().collect(Collectors.toList());

        IntStream.range(0, lignes.size())
                .forEach(value -> {
                    Assert.assertTrue(lignes.get(value).getBlocs().equals(lists.get(value)));
                });
    }

    @Test
    public void getLignesDepuisCruseur_SansLignesInserees() throws Exception {
        Environnement environnement = new Environnement();

        try {
            environnement.getLignesDepuisCurseur();
            Assert.assertFalse(true);
        } catch (ConstructionLigneOrdonnee constructionLigneOrdonnee) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getLignesDepuisCurseur_LongueurMax() throws Exception {
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

        IntStream.range(0, Configuration.EnvironnementLongueur.get_valeur())
            .forEach(value -> {
                try {
                    environnement.ajoutLigne(new Ligne(TypeLigne.EAU, blocs1));
                } catch (LigneNonRenseignee ligneNonRenseignee) {
                    ligneNonRenseignee.printStackTrace();
                }
            });

        Assert.assertTrue(environnement.getLignesCurseur() == Configuration.EnvironnementLongueur.get_valeur());

        //on ajoute une ligne supplémentaire
        Ligne derniereLigne = new Ligne(TypeLigne.ROUTE, blocs1);
        environnement.ajoutLigne(derniereLigne);
        Assert.assertTrue(environnement.getLignesCurseur() == 1);

        List<Ligne> lignes = environnement.getLignesDepuisCurseur().collect(Collectors.toList());
        Assert.assertTrue(lignes.size() == 50);
        Assert.assertTrue(lignes.get(lignes.size()-1).equals(derniereLigne));

    }

    @Test
    public void getLignesCurseur() throws Exception {
        Environnement environnement = new Environnement();
        Assert.assertTrue(environnement.getLignesCurseur() == 0);
    }

    @Test
    public void getLignes() throws Exception {
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
        List<Bloc> blocs6 = Arrays.asList(bloc1, bloc1, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs7 = Arrays.asList(bloc1, bloc2, bloc5, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs8 = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs9 = Arrays.asList(bloc3, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);
        List<Bloc> blocs10 = Arrays.asList(bloc2, bloc2, bloc3, bloc4, bloc5, bloc6, bloc7, bloc8);

        List<List<Bloc>> lists = Arrays.asList(blocs1, blocs2, blocs3, blocs4, blocs5);

        lists.forEach(blocs -> {
            try {
                environnement.ajoutLigne(new Ligne(TypeLigne.ARBRE, blocs));
            } catch (LigneNonRenseignee ligneNonRenseignee) {
                ligneNonRenseignee.printStackTrace();
            }
        });

        Assert.assertTrue(environnement.getLignes().size() == 5);

        IntStream.range(0, environnement.getLignes().size())
                .forEach(value -> {
                    Assert.assertTrue(environnement.getLignes().get(value).getBlocs()
                    .equals(lists.get(value)));
                });

    }

}