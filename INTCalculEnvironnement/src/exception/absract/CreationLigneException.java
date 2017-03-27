package exception.absract;

/**
 * Created by ldalzotto on 24/02/2017.
 */
public abstract class CreationLigneException extends Exception {

    public CreationLigneException(String message, Throwable cause){
        super(message, cause);
    }

    public CreationLigneException(Throwable cause){
        super(cause);
    }
}
