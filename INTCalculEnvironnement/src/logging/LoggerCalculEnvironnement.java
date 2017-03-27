package logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ldalzotto on 22/03/2017.
 */
public class LoggerCalculEnvironnement {

    LoggerCalculEnvironnement() {
        throw new InstantiationError("La classe ne peut pas être instanciée !");
    }

    public static boolean log(Logger logger, Level level, String message, Object... parametres){
        if(logger.isLoggable(level)){
            logger.log(level, String.format(message, parametres));
            return true;
        } else {
            return false;
        }
    }

}
