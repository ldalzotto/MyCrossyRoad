package enumeration;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public enum Configuration {

    EnvironnementLargeur(10),
    EnvironnementLongueur(50),
    DistanceDeltaOuverture(3);

    private int _valeur;

    Configuration(int valeur){
        _valeur = valeur;
    }

    public int get_valeur(){
        return _valeur;
    }

}
