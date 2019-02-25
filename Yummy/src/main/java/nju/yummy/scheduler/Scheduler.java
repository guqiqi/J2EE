package nju.yummy.scheduler;

import nju.yummy.entity.OrderEntity;
import nju.yummy.service.OrderService;
import nju.yummy.service.StatisticService;
import nju.yummy.serviceImpl.OrderServiceImpl;
import nju.yummy.serviceImpl.StatisticServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {
    private OrderService orderService;
    private StatisticService statisticService;

    public Scheduler() {
        orderService = new OrderServiceImpl();
        statisticService = new StatisticServiceImpl();
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void orderTimer() {
        //每分钟实现一次,判断自动收货和取消
        List<OrderEntity> orderEntityList = orderService.getAllOrders();

        for (OrderEntity orderEntity : orderEntityList) {
            // 确认收货
            if (orderEntity.getStatus() == 2 && getMinutes(orderEntity.getDeliverTime()) > 15)
                orderService.confirmReceive(orderEntity.getOrderId());

            if (orderEntity.getStatus() == 0 && getMinutes(orderEntity.getPlaceTime()) > 15)
                orderService.cancel(orderEntity.getOrderId());
        }
    }

    private int getMinutes(Date start) {
        long from = start.getTime();
        long to = new Date().getTime();
        int minutes = (int) ((to - from) / (1000 * 60));
        return minutes;
    }

    @Scheduled(cron = "0 52 10 * * ? ")
    public void checkProfit() {
        //每天实现一次,用于网站结算
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        statisticService.settleAccount();
    }
}
