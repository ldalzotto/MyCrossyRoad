package logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ldalzotto on 22/03/2017.
 */
public class LoggerCalculEnvironnement {

    private LoggerCalculEnvironnement() throws InstantiationException {
        throw new InstantiationException("La classe ne peut pas être instanciée !");
    }

    public static void log(Logger logger, Level level, String message, Object... parametres){
        if(logger.isLoggable(level)){
            logger.log(level, String.format(message, parametres));
        }
    }

}
