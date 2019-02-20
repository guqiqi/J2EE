package nju.yummy.serviceImpl;

import org.junit.Test;

import static org.junit.Assert.*;

public class SellerServiceImplTest {

    @Test
    public void getCostByCustomer() {
        System.out.println(new SellerServiceImpl().getCostByCustomer("XgZGiwT").size());
    }

    @Test
    public void getCostByHour() {
    }

    @Test
    public void getCostByTime() {
    }
}