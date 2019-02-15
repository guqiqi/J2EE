package nju.yummy.serviceImpl;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceImplTest {

    @Test
    public void prePlaceOrder() {
        List<Integer> foods = new ArrayList<>();
        foods.add(13);
        foods.add(15);

        List<Integer> amount = new ArrayList<>();
        amount.add(1);
        amount.add(1);
        new OrderServiceImpl().prePlaceOrder("222", "XgZGiwT", foods, amount);
    }

    @Test
    public void confirmReceive() {
        new OrderServiceImpl().confirmReceive("930001324810672107847242");
    }
}