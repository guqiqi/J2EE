package nju.yummy.daoImpl;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderDaoImplTest {

    @Test
    public void getOrderBySellerId() {
        new OrderDaoImpl().getOrderBySellerId("XgZGiwT");
    }
}