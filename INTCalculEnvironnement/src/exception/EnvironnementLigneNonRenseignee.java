package exception;

import exception.absract.CreationLigneException;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class EnvironnementLigneNonRenseignee extends CreationLigneException {
    public EnvironnementLigneNonRenseignee(String message, Throwable cause){
        super(message, cause);
    }
}
