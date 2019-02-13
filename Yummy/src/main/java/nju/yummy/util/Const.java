package nju.yummy.util;

import com.alibaba.fastjson.JSONArray;

public class Const {
    public static String regix = "#&#";

    public static String convertJSONArrayToString(JSONArray jsonArray) {
        Object[] array = jsonArray.toArray();
        String result = "";
        for (int i = 0; i < array.length - 1; i++) {
            result = result + array[i].toString() + regix;
        }
        result = result + array[array.length - 1];
        return result;
    }
}
