package nju.yummy.util;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Const {
    public static String regex = "#&#";

    public static double[] discount = new double[]{1, 0.98, 0.90};

    public static double cut = 0.95;

    public static String[] sellerType = new String[]{"快餐便当", "甜品饮品", "果蔬生鲜", "商店超市", "鲜花绿植"};

    public static String convertListToString(List<Integer> list){
        String result = "";
        for (int i = 0; i < list.size() - 1; i++) {
            result = result + list.get(i) + regex;
        }
        result = result + list.get(list.size() - 1);
        return result;
    }

    public static String convertJSONArrayToString(JSONArray jsonArray) {
        Object[] array = jsonArray.toArray();
        String result = "";
        for (int i = 0; i < array.length - 1; i++) {
            result = result + array[i].toString() + regex;
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
