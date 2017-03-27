package com.my.crossy.road.screen.util;

import INTEnvironnementManager.enumeration.TypeBlocAffichage;
import INTEnvironnementManager.modele.BlocAffichage;
import com.my.crossy.road.entity.Entity;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 18/03/2017.
 */
public class MainGameScreenUtilTest {
    @Test
    public void generateEntity() throws Exception {
        BlocAffichage blocAffichage = new BlocAffichage(TypeBlocAffichage.DECOR, false);
        Entity entity = MainGameScreenUtil.generateEntity(blocAffichage);

        Assert.assertTrue(entity != null);
        Assert.assertTrue(entity.get_physicsComponent() == null);

        Assert.assertTrue(true);
    }

}