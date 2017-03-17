package com.my.crossy.road.screen.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ldalzotto on 15/03/2017.
 */
public class Table3D<A,B,C> {

    private Map<A, Map<B, C>> _content = new HashMap<>();

    public void put(A value1,B value2, C value3){
        Map<B,C> cdMap = new HashMap<B, C>();
        cdMap.put(value2, value3);

        _content.put(value1, cdMap);
    }

    public List<Row3D> getOccurences(){
        List<Row3D> listReturn = new ArrayList<>();

        _content.forEach((a, bcMap) -> {
            bcMap.forEach((b, c) -> {
                Row3D row3D = new Row3D(a,b,c);
                listReturn.add(row3D);
            });
        });

        return listReturn;
    }

}
