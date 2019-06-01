package com.mcw.cora.base.trouble;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author yibi
 * Date 2019/6/1 11:36
 * Version 1.0
 **/
public class MapRemove {

    public static void main(String[] args) {
        Map<String, String> tmpMap = new HashMap<String, String>();
        tmpMap.put("dog", "dog");
        tmpMap.put("cat", "cat");
        tmpMap.put("tiger", "tiger");

        System.out.println(tmpMap.get(new ObjRemove()));

        tmpMap.remove(new ObjRemove());
        tmpMap.forEach((key, value) -> {
            System.out.println(key);
        });
    }

    static class ObjRemove {
        @Override
        public int hashCode() {
            return "dog".hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
