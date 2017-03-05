package exception;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class MalformedLineException extends LigneNonCree {
    public MalformedLineException(String message, Throwable cause){
        super(message,cause);
    }
}
