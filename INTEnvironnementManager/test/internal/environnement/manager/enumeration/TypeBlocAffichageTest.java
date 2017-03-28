package internal.environnement.manager.enumeration;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 28/03/2017.
 */
public class TypeBlocAffichageTest {
    @Test
    public void getValueFromString() throws Exception {
        int random = ThreadLocalRandom.current().nextInt(0, TypeBlocAffichage.values().length);
        TypeBlocAffichage reference = TypeBlocAffichage.values()[random];
        TypeBlocAffichage correction = TypeBlocAffichage.getValueFromString(reference.name());

        Assert.assertTrue(reference.equals(correction));
    }

}