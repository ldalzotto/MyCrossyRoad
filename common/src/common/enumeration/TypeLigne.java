package common.enumeration;

import com.badlogic.gdx.graphics.Color;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;

/**
 * Created by ldalzotto on 20/03/2017.
 */
public enum  TypeLigne {

    ROUTE(1, Color.GRAY),
    EAU(1, Color.BLUE),
    TRAIN(1, Color.RED),
    ARBRE(0, Color.GREEN);

    private int menace;
    private Color couleurCube;

    private static BinaryOperator<TypeLigne> randomTypeLigne = (typeLigne, typeLigne2) -> {
        int randomInt = ThreadLocalRandom.current().nextInt(0,2);
        if(randomInt == 0){
            return typeLigne;
        } else {
            return typeLigne2;
        }
    };

    TypeLigne(int menace, Color color){
        this.menace = menace;
        this.couleurCube = color;
    }

    public int getMenace() {
        return menace;
    }

    public Color getCouleurCube() {
        return couleurCube;
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
