package enumeration;

import exception.MalformedLineException;
import modele.Bloc;
import modele.Ligne;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class EnvironnementInitConfiguration{

    public static Ligne ligne_1, ligne_2, ligne_3, ligne_4, ligne_5, ligne_6, ligne_7, ligne_8, ligne_9, ligne_10;

    static {
        try {
            List<Bloc> initBlocList = new ArrayList<>();
            initBlocList.add(new Bloc(TypeBloc.Obstacle, false));
            initBlocList.add(new Bloc(TypeBloc.Obstacle, false));
            initBlocList.add(new Bloc(TypeBloc.Obstacle, false));

            initBlocList.add(new Bloc(TypeBloc.Decor, false));
            initBlocList.add(new Bloc(TypeBloc.Decor, true));
            initBlocList.add(new Bloc(TypeBloc.Decor, true));
            initBlocList.add(new Bloc(TypeBloc.Decor, false));

            initBlocList.add(new Bloc(TypeBloc.Obstacle, false));
            initBlocList.add(new Bloc(TypeBloc.Obstacle, false));
            initBlocList.add(new Bloc(TypeBloc.Obstacle, false));

            ligne_1 = new Ligne(0, TypeLigne.Arbre, initBlocList);
            ligne_2 = new Ligne(0, TypeLigne.Arbre, initBlocList);
            ligne_3 = new Ligne(0, TypeLigne.Arbre, initBlocList);
            ligne_4 = new Ligne(0, TypeLigne.Arbre, initBlocList);
            ligne_5 = new Ligne(0, TypeLigne.Arbre, initBlocList);
            ligne_6 = new Ligne(0, TypeLigne.Arbre, initBlocList);
            ligne_7 = new Ligne(0, TypeLigne.Arbre, initBlocList);
            ligne_8 = new Ligne(0, TypeLigne.Arbre, initBlocList);
            ligne_9 = new Ligne(0, TypeLigne.Arbre, initBlocList);
            ligne_10 = new Ligne(0, TypeLigne.Arbre, initBlocList);
        } catch (MalformedLineException e) {
            e.printStackTrace();
        }
    }

}
