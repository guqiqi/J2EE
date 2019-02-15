package nju.yummy.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import nju.yummy.entity.OrderEntity;
import nju.yummy.service.OrderService;
import nju.yummy.service.SellerService;
import nju.yummy.serviceImpl.OrderServiceImpl;
import nju.yummy.serviceImpl.SellerServiceImpl;
import nju.yummy.util.Const;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    private SellerService sellerService;

    public OrderController() {
        orderService = new OrderServiceImpl();
        sellerService = new SellerServiceImpl();
    }

    @ResponseBody
    @RequestMapping(value = "/pre/place", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String prePlaceOrder(@RequestBody JSONObject jsonParam) {
        String email = jsonParam.getString("email");
        String sellerId = jsonParam.getString("sellerId");

        List<Integer> foodIds = Const.convertJSONArrayToIntegerList(jsonParam.getJSONArray("foodIds"));
        List<Integer> amounts = Const.convertJSONArrayToIntegerList(jsonParam.getJSONArray("amount"));

        JSONObject result = new JSONObject();

        OrderEntity orderEntity = orderService.prePlaceOrder(email, sellerId, foodIds, amounts);
        result.put("totalMoney", orderEntity.getTotalMoney());
        result.put("payMoney", orderEntity.getPayMoney());

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/place", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String placeOrder(@RequestBody JSONObject jsonParam) {
        String email = jsonParam.getString("email");
        String sellerId = jsonParam.getString("sellerId");

        List<Integer> foodIds = Const.convertJSONArrayToIntegerList(jsonParam.getJSONArray("foodIds"));
        List<Integer> amounts = Const.convertJSONArrayToIntegerList(jsonParam.getJSONArray("amount"));

        Date reachTime = jsonParam.getDate("reachTime");
        Integer addressId = jsonParam.getInteger("addressId");

        JSONObject result = new JSONObject();

        OrderEntity orderEntity = orderService.placeOrder(email, sellerId, foodIds, amounts, reachTime, addressId);
        result.put("totalMoney", orderEntity.getTotalMoney());
        result.put("payMoney", orderEntity.getPayMoney());
        result.put("orderId", orderEntity.getOrderId());

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/pay", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String payOrder(String orderId) {
        // TODO
        JSONObject result = new JSONObject();

        result.put("isSuccess", orderService.pay(orderId));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/cancel", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String cancelOrder(String orderId) {
        // TODO
        JSONObject result = new JSONObject();

        result.put("isSuccess", orderService.cancel(orderId));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/deliver", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String startDeliverOrder(String orderId) {
        // TODO
        JSONObject result = new JSONObject();

        result.put("isSuccess", orderService.startDeliver(orderId));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/finish", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String finishOrder(String orderId) {
        // TODO
        JSONObject result = new JSONObject();

        result.put("isSuccess", orderService.confirmReceive(orderId));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/customer/order", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getCustomerOrder(String email) {
        // TODO

        JSONObject result = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        List<OrderEntity> orderEntities = orderService.getCustomerOrders(email);

        for(OrderEntity orderEntity: orderEntities){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orderId", orderEntity.getOrderId());
            jsonObject.put("sellerName", sellerService.getSellerInfo(orderEntity.getSellerId()).getName());
            jsonObject.put("placeTime", orderEntity.getPlaceTime());
            jsonObject.put("status", orderEntity.getStatus());
            jsonObject.put("payMoney", orderEntity.getPayMoney());

            jsonArray.add(jsonObject);
        }

        result.put("orders", jsonArray);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/seller/order", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getSellerOrder(String sellerId) {
        JSONObject result = new JSONObject();

        result.put("orders", orderService.getSellerOrders(sellerId));

        return result.toJSONString();
    }

}
