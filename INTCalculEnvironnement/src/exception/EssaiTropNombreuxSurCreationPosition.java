package exception;

import exception.absract.CreationLigneException;

/**
 * Created by ldalzotto on 23/02/2017.
 */
public class EssaiTropNombreuxSurCreationPosition extends CreationLigneException {
    public EssaiTropNombreuxSurCreationPosition(String message, Throwable cause){
        super(message, cause);
    }
}
