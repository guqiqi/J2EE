package nju.yummy.util;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Const {
    public static String regix = "#&#";

    public static double[] discount = new double[]{1, 0.98, 0.90};

    public static String convertJSONArrayToString(JSONArray jsonArray) {
        Object[] array = jsonArray.toArray();
        String result = "";
        for (int i = 0; i < array.length - 1; i++) {
            result = result + array[i].toString() + regix;
        }
        result = result + array[array.length - 1];
        return result;
    }

    public static List<Integer> convertJSONArrayToIntegerList(JSONArray jsonArray) {
        Object[] array = jsonArray.toArray();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            result.add(Integer.parseInt(array[i].toString()));
        }
        return result;
    }
}
