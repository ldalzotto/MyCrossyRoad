package com.my.crossy.road.screen.util;

import java.util.*;

/**
 * Created by ldalzotto on 15/03/2017.
 */
public class Table4D<A,B,C,D> {

    public static String KEY_1 = "key1";
    public static String KEY_2 = "key2";
    public static String KEY_3 = "key3";
    public static String KEY_4 = "key4";


    private Map<A, Map<B, Map<C, D>>> _content = new HashMap<>();

    public void put(A value1,B value2, C value3, D value4){
        Map<C,D> cdMap = new HashMap<C, D>();
        cdMap.put(value3, value4);

        Map<B, Map<C, D>> bMapMap = new HashMap<B, Map<C, D>>();
        bMapMap.put(value2, cdMap);

        _content.put(value1, bMapMap);
    }

    public List<Row4D> getOccurences(){
        List<Row4D> listReturn = new ArrayList<>();

        _content.forEach((a, bMapMap) -> {
            bMapMap.forEach((b, cdMap) -> {
                cdMap.forEach((c, d) -> {
                    Row4D row4D = new Row4D(a, b, c, d);
                    listReturn.add(row4D);
                });
            });
        });

        return listReturn;
    }

}
