package enumeration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.function.BinaryOperator;

import static enumeration.TypeLigne.Arbre;

/**
 * Created by ldalzotto on 20/02/2017.
 */
public class TypeLigneTest {

    @Test
    public void RANDOM_TYPE_LIGNE_TEST(){
        try {
            Field f = TypeLigne.Arbre.getClass().getDeclaredField("RANDOM_TYPE_LIGNE");
            f.setAccessible(true);
            BinaryOperator<TypeLigne> RANDOM_TYPE_LIGNE = (BinaryOperator<TypeLigne>)f.get(TypeLigne.Arbre);

            TypeLigne randomTypeLigne = RANDOM_TYPE_LIGNE.apply(TypeLigne.Arbre, TypeLigne.Eau);

            if(randomTypeLigne.equals(TypeLigne.Arbre) || randomTypeLigne.equals(TypeLigne.Eau)){
                Assert.assertTrue(true);
            } else {
                Assert.assertFalse(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTypeAleatoire_TEST(){
        TypeLigne menace_0_typeLigne = TypeLigne.getTypeAleatoire(0);

        if(!menace_0_typeLigne.equals(TypeLigne.Arbre)){
            Assert.assertFalse(true);
        }

        TypeLigne menace_1_typeLigne = TypeLigne.getTypeAleatoire(1);

        if(menace_1_typeLigne.equals(TypeLigne.Arbre)){
            Assert.assertFalse(true);
        }

    }

}
