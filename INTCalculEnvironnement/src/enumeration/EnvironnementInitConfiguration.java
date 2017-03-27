package enumeration;

import common.enumeration.TypeLigne;
import modele.Bloc;
import modele.Ligne;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldalzotto on 19/02/2017.
 */
class EnvironnementInitConfiguration{

    static final Ligne ligne_1;
    static final Ligne ligne_2;
    static final Ligne ligne_3;
    static final Ligne ligne_4;
    static final Ligne ligne_5;
    static final Ligne ligne_6;
    static final Ligne ligne_7;
    static final Ligne ligne_8;
    static final Ligne ligne_9;
    static final Ligne ligne_10;

    EnvironnementInitConfiguration(){
        throw new InstantiationError("This class cannot be instantiated");
    }

    static {
            List<Bloc> initBlocList = new ArrayList<>();
        initBlocList.add(new Bloc(TypeBloc.PHANTOM_OBSTACLE, false));


        initBlocList.add(new Bloc(TypeBloc.OBSTACLE, false));
            initBlocList.add(new Bloc(TypeBloc.OBSTACLE, false));
            initBlocList.add(new Bloc(TypeBloc.OBSTACLE, false));

            initBlocList.add(new Bloc(TypeBloc.DECOR, false));
            initBlocList.add(new Bloc(TypeBloc.DECOR, true));
            initBlocList.add(new Bloc(TypeBloc.DECOR, true));
            initBlocList.add(new Bloc(TypeBloc.DECOR, false));

            initBlocList.add(new Bloc(TypeBloc.OBSTACLE, false));
            initBlocList.add(new Bloc(TypeBloc.OBSTACLE, false));
            initBlocList.add(new Bloc(TypeBloc.OBSTACLE, false));

        initBlocList.add(new Bloc(TypeBloc.PHANTOM_OBSTACLE, false));

            ligne_1 = new Ligne(TypeLigne.ARBRE, initBlocList);
            ligne_2 = new Ligne(TypeLigne.ARBRE, initBlocList);
            ligne_3 = new Ligne(TypeLigne.ARBRE, initBlocList);
            ligne_4 = new Ligne(TypeLigne.ARBRE, initBlocList);
            ligne_5 = new Ligne(TypeLigne.ARBRE, initBlocList);
            ligne_6 = new Ligne(TypeLigne.ARBRE, initBlocList);
            ligne_7 = new Ligne(TypeLigne.ARBRE, initBlocList);
            ligne_8 = new Ligne(TypeLigne.ARBRE, initBlocList);
            ligne_9 = new Ligne(TypeLigne.ARBRE, initBlocList);
            ligne_10 = new Ligne(TypeLigne.ARBRE, initBlocList);
    }

}
