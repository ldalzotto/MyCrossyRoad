package enumeration;

import modele.Ligne;


/**
 * Created by ldalzotto on 19/02/2017.
 */
public enum EnvironnementInit {

    LIGNE_1(EnvironnementInitConfiguration.ligne_1),
    LIGNE_2(EnvironnementInitConfiguration.ligne_2),
    LIGNE_3(EnvironnementInitConfiguration.ligne_3),
    LIGNE_4(EnvironnementInitConfiguration.ligne_4),
    LIGNE_5(EnvironnementInitConfiguration.ligne_5),
    LIGNE_6(EnvironnementInitConfiguration.ligne_6),
    LIGNE_7(EnvironnementInitConfiguration.ligne_7),
    LIGNE_8(EnvironnementInitConfiguration.ligne_8),
    LIGNE_9(EnvironnementInitConfiguration.ligne_9),
    LIGNE_10(EnvironnementInitConfiguration.ligne_10);

    private Ligne _ligne;

    EnvironnementInit(Ligne ligne){
        _ligne = ligne;
    }

    public Ligne get_ligne(){
        return _ligne;
    }


}


