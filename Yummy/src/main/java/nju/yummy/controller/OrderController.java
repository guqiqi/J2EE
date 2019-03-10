package nju.yummy.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import nju.yummy.entity.AddressEntity;
import nju.yummy.entity.OrderEntity;
import nju.yummy.entity.SellerEntity;
import nju.yummy.service.CustomerService;
import nju.yummy.service.OrderService;
import nju.yummy.service.SellerService;
import nju.yummy.serviceImpl.AddressServiceImpl;
import nju.yummy.serviceImpl.CustomerServiceImpl;
import nju.yummy.serviceImpl.OrderServiceImpl;
import nju.yummy.serviceImpl.SellerServiceImpl;
import nju.yummy.util.Const;
import nju.yummy.vo.Location;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    private SellerService sellerService;
    private CustomerService customerService;

    public OrderController() {
        orderService = new OrderServiceImpl();
        sellerService = new SellerServiceImpl();
        customerService = new CustomerServiceImpl();
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
    public String placeOrder(@RequestBody JSONObject jsonParam) throws ParseException {
        String email = jsonParam.getString("email");
        String sellerId = jsonParam.getString("sellerId");

        List<Integer> foodIds = Const.convertJSONArrayToIntegerList(jsonParam.getJSONArray("foodIds"));
        List<Integer> amounts = Const.convertJSONArrayToIntegerList(jsonParam.getJSONArray("amount"));

        String reachTime = jsonParam.getString("reachTime");
        System.out.println(reachTime);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date time = sdf.parse(reachTime);
        time.setYear(new Date().getYear());
        time.setMonth(new Date().getMonth());
        time.setDate(new Date().getDate());
        System.out.println(time);

        Integer addressId = jsonParam.getInteger("addressId");

        JSONObject result = new JSONObject();

        OrderEntity orderEntity = orderService.placeOrder(email, sellerId, foodIds, amounts, time, addressId);
        result.put("totalMoney", orderEntity.getTotalMoney());
        result.put("payMoney", orderEntity.getPayMoney());
        result.put("orderId", orderEntity.getOrderId());

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/address/judge", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getAddress(String email, String sellerId) {
        List<AddressEntity> addressEntityList = customerService.getAddressByEmail(email);

        SellerEntity sellerEntity = sellerService.getSellerInfo(sellerId);
        Location sellerLocation = new Location(sellerEntity.getLongitude(), sellerEntity.getLatitude());

        JSONObject result = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for (AddressEntity addressEntity : addressEntityList) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("addressId", addressEntity.getAddressId());
            jsonObject.put("detail", addressEntity.getDetail());
            jsonObject.put("phone", addressEntity.getPhone());
            jsonObject.put("receiver", addressEntity.getReceiver());

            Location receiverLocation = new Location(addressEntity.getLongitude(), addressEntity.getLatitude());
            jsonObject.put("canSend", new AddressServiceImpl().judgeCanSend(sellerLocation, receiverLocation));

            jsonArray.add(jsonObject);
        }

        result.put("addressList", jsonArray);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/pay", method = RequestMethod.PATCH, produces = "application/json;charset=UTF-8")
    public String payOrder(String orderId) {
        JSONObject result = new JSONObject();

        result.put("isSuccess", orderService.pay(orderId));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/cancel", method = RequestMethod.PATCH, produces = "application/json;charset=UTF-8")
    public String cancelOrder(String orderId) {
        JSONObject result = new JSONObject();

        result.put("isSuccess", orderService.cancel(orderId));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/deliver", method = RequestMethod.PATCH, produces = "application/json;charset=UTF-8")
    public String startDeliverOrder(String orderId) {
        JSONObject result = new JSONObject();

        result.put("isSuccess", orderService.startDeliver(orderId));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/finish", method = RequestMethod.PATCH, produces = "application/json;charset=UTF-8")
    public String finishOrder(String orderId) {
        JSONObject result = new JSONObject();

        result.put("isSuccess", orderService.confirmReceive(orderId));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/customer/order", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getCustomerOrder(String email) {
        JSONObject result = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        List<OrderEntity> orderEntities = orderService.getCustomerOrders(email);

        for (OrderEntity orderEntity : orderEntities) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orderId", orderEntity.getOrderId());
            jsonObject.put("sellerName", sellerService.getSellerInfo(orderEntity.getSellerId()).getName());
            jsonObject.put("placeTime", orderEntity.getPlaceTime());
            jsonObject.put("status", orderEntity.getStatus());
            jsonObject.put("payMoney", new BigDecimal(orderEntity.getPayMoney()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            jsonObject.put("foods", getOrderDetail(orderEntity.getFoodIds(), orderEntity.getFoodNumbers()));

            jsonArray.add(jsonObject);
        }

        result.put("orders", jsonArray);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/seller/order", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getSellerOrder(String sellerId) {
        JSONObject result = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        List<OrderEntity> orderEntities = orderService.getSellerOrders(sellerId);

        for (OrderEntity orderEntity : orderEntities) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orderId", orderEntity.getOrderId());
            jsonObject.put("placeTime", orderEntity.getPlaceTime());
            jsonObject.put("status", orderEntity.getStatus());
            jsonObject.put("payMoney", new BigDecimal(orderEntity.getPayMoney()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            jsonObject.put("receiveTime", orderEntity.getReachTime());

            jsonObject.put("foods", getOrderDetail(orderEntity.getFoodIds(), orderEntity.getFoodNumbers()));

            AddressEntity addressEntity = customerService.getAddressById(orderEntity.getAddressId());

            jsonObject.put("receiver", addressEntity.getReceiver());
            jsonObject.put("detail", addressEntity.getDetail());
            jsonObject.put("phone", addressEntity.getPhone());

            jsonArray.add(jsonObject);
        }

        result.put("orders", jsonArray);

        return result.toJSONString();
    }

    private JSONArray getOrderDetail(String foodId, String foodNumber){
        int[] foodIds = Const.convertStringToInts(foodId);
        int[] fondNumbers = Const.convertStringToInts(foodNumber);

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < foodIds.length; i++) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("foodId", foodIds[i]);
            jsonObject.put("foodName", sellerService.getFoodInfo(foodIds[i]).getName());
            jsonObject.put("foodNumber", fondNumbers[i]);
            jsonObject.put("money", sellerService.getFoodInfo(foodIds[i]).getMoney());

            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }
}
