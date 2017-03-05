package exception;

import exception.absract.CreationLigneException;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class LigneNonCree extends CreationLigneException {
    public LigneNonCree(String message, Throwable cause){
        super(message,cause);
    }
    public LigneNonCree(Throwable cause){
        super(cause);
    }
}
