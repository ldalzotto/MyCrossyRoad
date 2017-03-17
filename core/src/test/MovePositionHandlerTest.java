import com.badlogic.gdx.math.Vector3;
import com.my.crossy.road.screen.util.MovePositionHandler;
import org.junit.Test;

/**
 * Created by ldalzotto on 15/03/2017.
 */
public class MovePositionHandlerTest {

    @Test
    public void MovePositionHandlerTest(){
        Vector3 initialPosition = new Vector3(0,0,0);
        Vector3 finalPosition = new Vector3(0,0,5);

        Vector3 speed = new Vector3(0,0,0.1f);


        MovePositionHandler movePositionHandler = new MovePositionHandler(initialPosition,
                    finalPosition, speed);


        movePositionHandler.updatePosition(0.0025f);
        movePositionHandler.updatePosition(0.25f);
    }

    @Test
    public void testWithNegativeValues(){
        Vector3 finalPosition = new Vector3(0,0,0);
        Vector3 initialPosition = new Vector3(0,0,5);

        Vector3 speed = new Vector3(0,0,-0.1f);


        MovePositionHandler movePositionHandler = new MovePositionHandler(initialPosition,
                finalPosition, speed);


        movePositionHandler.updatePosition(0.0025f);
        movePositionHandler.updatePosition(0.25f);
    }

}
