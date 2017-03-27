package logging;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 27/03/2017.
 */
public class LoggerCalculEnvironnementTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test(expected = InstantiationError.class)
    public void cannotInstatiate(){
        LoggerCalculEnvironnement loggerCalculEnvironnement = new LoggerCalculEnvironnement();
    }

    @Test
    public void log() throws Exception {
        Logger logger = Logger.getLogger(LoggerCalculEnvironnementTest.class.getSimpleName());
        logger.setLevel(Level.FINEST);

        boolean b = LoggerCalculEnvironnement.log(logger, Level.FINEST, "message");
        Assert.assertTrue(b);

        logger.setLevel(Level.INFO);
        boolean c = LoggerCalculEnvironnement.log(logger, Level.FINEST, "message");
        Assert.assertFalse(c);
    }

}