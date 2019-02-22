package nju.yummy.util;

import nju.yummy.entity.OrderEntity;
import nju.yummy.vo.SellerCostVO;

import java.util.ArrayList;
import java.util.List;

public class StatisticUtil {
    public static double[] getCostByTime(List<OrderEntity> orderEntityList) {
        double[] result = new double[3];

        for (OrderEntity orderEntity : orderEntityList) {
            if (orderEntity.getStatus() != -1) {
                long minutes = (System.currentTimeMillis() - orderEntity.getPlaceTime().getTime()) / (1000 * 60);

                if (minutes <= 7 * 24 * 60)
                    result[0] += orderEntity.getPayMoney();
                if (minutes <= 30 * 24 * 60)
                    result[1] += orderEntity.getPayMoney();
                if (minutes <= 365 * 24 * 60)
                    result[2] += orderEntity.getPayMoney();
            }
        }

        return result;
    }

    public static List<Double> getCostBy2Hour(double[] costByHour){
        List<Double> costBy2Hour = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            costBy2Hour.add(costByHour[2*i] + costByHour[i*2+1]);
        }

        return costBy2Hour;
    }

    public static double[] getMoneyByType(List<SellerCostVO> sellerCostVOList){
        double[] moneyByType = new double[5];
        for(int i = 0; i < 5; i++){
            moneyByType[i] = sellerCostVOList.get(i).getCost();
        }

        return moneyByType;
    }

    public static int[] getTimesByType(List<SellerCostVO> sellerCostVOList){
        int[] timesByType = new int[5];
        for(int i = 0; i < 5; i++){
            timesByType[i] = sellerCostVOList.get(i).getTimes();
        }

        return timesByType;
    }
}
