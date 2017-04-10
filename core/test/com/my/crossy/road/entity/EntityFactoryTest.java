package com.my.crossy.road.entity;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 18/03/2017.
 */
public class EntityFactoryTest {

    @Test(expected = InstantiationError.class)
    public void cannotInstatiate() throws Exception {
        new EntityFactory();
        Assert.assertFalse(true);
    }

}