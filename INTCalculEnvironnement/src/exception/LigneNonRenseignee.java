package exception;

import exception.absract.CreationLigneException;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class LigneNonRenseignee extends CreationLigneException {
    public LigneNonRenseignee(String message, Throwable cause){
        super(message, cause);
    }
}
