package com.my.crossy.road.screen.util;

/**
 * Created by ldalzotto on 15/03/2017.
 */
public class Row4D<A,B,C,D> {
        private A _key1;
        private B _key2;
        private C _key3;
        private D _key4;


        public Row4D(A key1, B key2, C key3, D key4){
            _key1 = key1;
            _key2 = key2;
            _key3 = key3;
            _key4 = key4;
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

    public D get_key4() {
        return _key4;
    }
}
