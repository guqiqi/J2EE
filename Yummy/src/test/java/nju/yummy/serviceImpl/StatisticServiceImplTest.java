package nju.yummy.serviceImpl;

import org.junit.Test;

import static org.junit.Assert.*;

public class StatisticServiceImplTest {

    @Test
    public void getCostClassifiedByType() {
        System.out.println(new StatisticServiceImpl().getCostClassifiedByType());
    }

    @Test
    public void getSellerNumberClassifiedByType() {
        System.out.println(new StatisticServiceImpl().getSellerNumberClassifiedByType());
    }

    @Test
    public void getCostByTime() {
    }

    @Test
    public void getCostByHour() {
    }

    @Test
    public void getSellerNumber() {
    }

    @Test
    public void getCustomerNumber() {
    }
}