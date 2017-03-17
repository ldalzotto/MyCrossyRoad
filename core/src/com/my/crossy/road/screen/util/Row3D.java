package com.my.crossy.road.screen.util;

/**
 * Created by ldalzotto on 15/03/2017.
 */
public class Row3D<A,B,C> {
    private A _key1;
    private B _key2;
    private C _key3;


    public Row3D(A key1, B key2, C key3){
        _key1 = key1;
        _key2 = key2;
        _key3 = key3;
    }

    public A get_key1(){
        return _key1;
    }

    public B get_key2() {
        return _key2;
    }

    public C get_key3() {
        return _key3;
    }
}
