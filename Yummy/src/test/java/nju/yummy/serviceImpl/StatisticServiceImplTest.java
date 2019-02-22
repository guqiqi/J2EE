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

    @Test
    public void getCustomerIncrease() {
        int[] result = new StatisticServiceImpl().getCustomerIncrease();
        for (int i : result) {
            System.out.println(i);
        }
    }

    @Test
    public void getSellerIncrease() {
        int[] result = new StatisticServiceImpl().getSellerIncrease();
        for (int i : result) {
            System.out.println(i);
        }
    }
}