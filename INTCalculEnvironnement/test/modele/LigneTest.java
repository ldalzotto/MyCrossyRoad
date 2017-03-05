package modele;

import enumeration.TypeBloc;
import enumeration.TypeLigne;
import exception.MalformedLineException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ldalzotto on 20/02/2017.
 */
public class LigneTest {

    @Test
    public void getOuvertures_TEST(){
        Bloc bloc1 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc2 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc3 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc4 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc5 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc6 = new Bloc(TypeBloc.Decor, false);

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6);

        Ligne ligne = null;
        try {
            ligne = new Ligne(0, TypeLigne.Arbre, blocs);
        } catch (MalformedLineException e) {
            e.printStackTrace();
            Assert.assertFalse(true);
        }

        List<Bloc> blocsOuverture = ligne.getOuvertures();
        if(blocsOuverture.size() != 3){
            Assert.assertFalse(true);
        }

    }

    @Test
    public void getOuverturesIndex_TEST(){
        Bloc bloc1 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc2 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc3 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc4 = new Bloc(TypeBloc.Decor, false);
        Bloc bloc5 = new Bloc(TypeBloc.Decor, true);
        Bloc bloc6 = new Bloc(TypeBloc.Decor, false);

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2, bloc3, bloc4, bloc5, bloc6);

        Ligne ligne = null;
        try {
            ligne = new Ligne(0, TypeLigne.Arbre, blocs);
        } catch (MalformedLineException e) {
            e.printStackTrace();
            Assert.assertFalse(true);
        }

        List<Integer> ouvertureIndex = ligne.getOuverturesIndex();

        if(ouvertureIndex.get(0) != 0){
            Assert.assertFalse(true);
        }
        if(ouvertureIndex.get(1) != 2){
            Assert.assertFalse(true);
        }
        if(ouvertureIndex.get(2) != 4){
            Assert.assertFalse(true);
        }

    }

}
