package nju.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import nju.yummy.service.StatisticService;
import nju.yummy.serviceImpl.StatisticServiceImpl;
import nju.yummy.util.StatisticUtil;
import nju.yummy.vo.ProfitVO;
import nju.yummy.vo.SellerCostVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    private StatisticService statisticService;

    public StatisticController(){
        this.statisticService = new StatisticServiceImpl();
    }

    @ResponseBody
    @RequestMapping(value = "/customer", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getCustomerStatistic() {
        JSONObject result = new JSONObject();

        result.put("customerNumber", statisticService.getCustomerNumber());
        result.put("customerIncrease", statisticService.getCustomerIncrease());

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/seller", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getSellerStatistic() {
        JSONObject result = new JSONObject();

        result.put("sellerNumber", statisticService.getSellerNumber());
        result.put("sellerNumberByType", statisticService.getSellerNumberClassifiedByType());
        result.put("sellerIncrease", statisticService.getSellerIncrease());

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/profit", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getProfitStatistic() {
        JSONObject result = new JSONObject();

        result.put("recentVolume", statisticService.getCostByTime());

        double[] volumeByHour = statisticService.getCostByHour();

        result.put("volumeByHour", StatisticUtil.getCostBy2Hour(volumeByHour));

        List<SellerCostVO> sellerCostVOList = statisticService.getCostClassifiedByType();

        result.put("volumeByType", StatisticUtil.getMoneyByType(sellerCostVOList));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/profit/daily", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getDailyProfitStatistic() {
        JSONObject result = new JSONObject();

        List<ProfitVO> profitVOList = statisticService.getDailyProfit();
        result.put("sellerTableData", profitVOList);
        result.put("profit", getTotalProfit(profitVOList));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/profit/monthly", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getMonthlyProfitStatistic() {
        JSONObject result = new JSONObject();

        List<ProfitVO> profitVOList = statisticService.getMonthlyProfit();
        result.put("sellerTableData", profitVOList);
        result.put("profit", getTotalProfit(profitVOList));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/profit/total", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getTotalProfitStatistic() {
        JSONObject result = new JSONObject();

        List<ProfitVO> profitVOList = statisticService.getTotalProfit();
        result.put("sellerTableData", profitVOList);
        result.put("profit", getTotalProfit(profitVOList));

        return result.toJSONString();
    }

    private double getTotalProfit(List<ProfitVO> profitVOS){
        double total = 0.0;
        for (ProfitVO profitVO: profitVOS) {
            total += profitVO.getProfit();
        }

        return (double) Math.round(total * 100) / 100;
    }
}
