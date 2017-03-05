package INTEnvironnementManager.enumeration;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public enum TypeBlocAffichage {
    Decor,Obstacle;

    public static TypeBlocAffichage getValueFromString(String value){
        return Arrays.asList(TypeBlocAffichage.values())
                .stream()
                .filter(typeBlocAffichage -> {
                    return typeBlocAffichage.name().equals(value);
                }).findAny()
                .orElse(null);
    }

}
