package main;

import calcul.INTCalculEnvironnement;
import common.enumeration.TypeLigne;
import enumeration.TypeBloc;
import exception.LigneNonCree;
import modele.Bloc;
import modele.Ligne;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class Main {

    public static void main(String[] args){

        List<Bloc> blocList = new ArrayList<>();
        blocList.add(new Bloc(TypeBloc.Decor, false));
        blocList.add(new Bloc(TypeBloc.Decor, true));
        blocList.add(new Bloc(TypeBloc.Decor, false));
        blocList.add(new Bloc(TypeBloc.Decor, true));

        Ligne ligne = null;

            ligne = new Ligne(TypeLigne.EAU, blocList);
            List<Bloc> blocs = ligne.getOuvertures();
            List<Integer> integers = ligne.getOuverturesIndex();
            System.out.println(integers);

        INTCalculEnvironnement intCalculEnvironnement = new INTCalculEnvironnement();
        intCalculEnvironnement.initialisationEnvironnement();
        try {
            intCalculEnvironnement.creationLigne();
        } catch (LigneNonCree ligneNonCree) {
            ligneNonCree.printStackTrace();
        }

        IntStream.range(0, 70).forEach(i1 -> {
            try {
                intCalculEnvironnement.creationLigne();
            } catch (LigneNonCree ligneNonCree) {
                ligneNonCree.printStackTrace();
            }
        });

        System.out.println("END");

    }

}
