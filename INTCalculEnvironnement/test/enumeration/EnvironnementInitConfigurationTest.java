package enumeration;

import org.junit.Test;

/**
 * Created by ldalzotto on 27/03/2017.
 */
public class EnvironnementInitConfigurationTest {

    @Test(expected = InstantiationError.class)
    public void cannotInstantiate(){
        EnvironnementInitConfiguration environnementInitConfiguration = new EnvironnementInitConfiguration();
    }

}
