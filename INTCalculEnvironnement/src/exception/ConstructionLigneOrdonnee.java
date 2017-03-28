package exception;

/**
 * Created by ldalzotto on 25/02/2017.
 */
public class ConstructionLigneOrdonnee extends RuntimeException {
    public ConstructionLigneOrdonnee(String message, Throwable cause){
        super(message, cause);
    }
}
