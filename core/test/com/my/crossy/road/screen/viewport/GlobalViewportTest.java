package com.my.crossy.road.screen.viewport;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ldalzotto on 06/04/2017.
 */
public class GlobalViewportTest {

    @Test(expected = InstantiationError.class)
    public void globalViewPort_error() throws Exception {
        new GlobalViewport.VIEWPORT();
    }

}