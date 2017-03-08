package enumeration;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public enum TypeLigne {

    Route(1),
    Eau(1),
    Train(1),
    Arbre(0);

    private int _menace;

    private static BinaryOperator<TypeLigne> RANDOM_TYPE_LIGNE = (((typeLigne, typeLigne2) -> {
        int randomInt = ThreadLocalRandom.current().nextInt(0,2);
        if(randomInt == 0){
            return typeLigne;
        } else {
            return typeLigne2;
        }
    }));

    TypeLigne(int menace){
        _menace = menace;
    }

    public int get_menace() {
        return _menace;
    }

    public static TypeLigne getTypeAleatoire(final int menace){

        return Arrays.asList(TypeLigne.values()).stream()

                .filter((typeLigne) -> {return (typeLigne.get_menace() == menace);})
                .reduce(RANDOM_TYPE_LIGNE)
                .get();
    }

}
