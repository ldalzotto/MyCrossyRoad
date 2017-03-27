package modele;

import enumeration.TypeBloc;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ldalzotto on 20/03/2017.
 */
public class BlocTest {
    @Test
    public void getIsOuverture() throws Exception {
        Bloc bloc = new Bloc(TypeBloc.DECOR, false);
        Boolean isOuverture = bloc.getIsOuverture();

        Assert.assertTrue(!isOuverture);
    }

    @Test
    public void getTypeBloc() throws Exception {
        Bloc bloc = new Bloc(TypeBloc.DECOR, false);
        TypeBloc typeBloc = bloc.getTypeBloc();

        Assert.assertTrue(typeBloc.equals(TypeBloc.DECOR));
    }

}