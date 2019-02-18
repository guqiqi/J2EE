package nju.yummy.serviceImpl;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerServiceImplTest {

    @Test
    public void getCostByHour() {
        System.out.println(new CustomerServiceImpl().getCostByHour("222")[7]);
    }

    @Test
    public void getCostClassifiedByType() {
    }

    @Test
    public void getCostBySeller() {
    }

    @Test
    public void getCostByTime() {
        new CustomerServiceImpl().getCostByTime("222");
    }

    @Test
    public void getCancelByUser() {
    }
}