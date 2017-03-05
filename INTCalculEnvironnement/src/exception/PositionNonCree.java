package exception;

import exception.absract.CreationLigneException;

/**
 * Created by ldalzotto on 24/02/2017.
 */
public class PositionNonCree extends CreationLigneException {
    public PositionNonCree(String messae, Throwable cause){
        super(messae, cause);
    }
}
