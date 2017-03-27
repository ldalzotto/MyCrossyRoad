package enumeration;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public enum Configuration {

    ENVIRONNEMENT_LARGEUR(10),
    ENVIRONNEMENT_LONGUEUR(50),
    DISTANCE_DELTA_OUVERTURE(3);

    private int valeur;

    Configuration(int valeur){
        this.valeur = valeur;
    }

    public int getValeur(){
        return valeur;
    }

}
