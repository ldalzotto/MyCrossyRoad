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

    @Test
    public void copySuccess() throws Exception {
        Bloc bloc = new Bloc(TypeBloc.DECOR, false);

        Bloc bloc1 = bloc.copy();

        Assert.assertTrue(bloc.hashCode() != bloc1.hashCode());
    }

    @Test(expected = RuntimeException.class)
    public void copyFail() throws Exception {
        Bloc bloc = new Bloc(null, false);
        bloc.copy();
    }

}