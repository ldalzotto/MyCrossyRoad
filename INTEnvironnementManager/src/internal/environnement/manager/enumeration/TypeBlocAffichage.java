package internal.environnement.manager.enumeration;

import java.util.Arrays;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public enum TypeBlocAffichage {
    DECOR, OBSTACLE, PHANTOM_OBSTACLE;

    public static TypeBlocAffichage getValueFromString(String value){
        return Arrays.stream(TypeBlocAffichage.values())
                .filter(typeBlocAffichage -> typeBlocAffichage.name().equals(value)).findAny()
                .orElse(null);
    }

}
