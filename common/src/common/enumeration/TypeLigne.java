package common.enumeration;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;

/**
 * Created by ldalzotto on 20/03/2017.
 */
public enum  TypeLigne {

    ROUTE(1),
    EAU(1),
    TRAIN(1),
    ARBRE(0);

    private int menace;

    private static BinaryOperator<TypeLigne> randomTypeLigne = (typeLigne, typeLigne2) -> {
        int randomInt = ThreadLocalRandom.current().nextInt(0,2);
        if(randomInt == 0){
            return typeLigne;
        } else {
            return typeLigne2;
        }
    };

    TypeLigne(int menace){
        this.menace = menace;
    }

    public int getMenace() {
        return menace;
    }

    public static TypeLigne getTypeAleatoire(final int menace){

        Optional<TypeLigne> typeLigneOptional = Arrays.stream(TypeLigne.values())
                .filter(typeLigne -> typeLigne.getMenace() == menace)
                .reduce(randomTypeLigne);

        if(typeLigneOptional.isPresent()){
            return typeLigneOptional.get();
        }
        return null;
    }
}
