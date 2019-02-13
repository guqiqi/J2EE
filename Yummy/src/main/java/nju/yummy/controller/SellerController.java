package nju.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import nju.yummy.entity.SellerEntity;
import nju.yummy.service.SellerService;
import nju.yummy.serviceImpl.SellerServiceImpl;
import nju.yummy.util.Const;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {
    private SellerService sellerService;

    public SellerController(){
        sellerService = new SellerServiceImpl();
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String login(@RequestBody JSONObject jsonParam) {
        String sellerId = jsonParam.getString("sellerId");
        String password = jsonParam.getString("password");

        JSONObject result = new JSONObject();

        String mes = sellerService.login(sellerId, password);

        result.put("isValid", mes.equals("成功登陆"));
        result.put("mes", mes);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String register(@RequestBody JSONObject jsonParam) {
        String name = jsonParam.getString("name");
        String password = jsonParam.getString("password");
        String type = jsonParam.getString("type");
        String address = jsonParam.getString("address");
        String phone = jsonParam.getString("phone");
        String startHour = jsonParam.getString("startHour");
        String endHour = jsonParam.getString("endHour");
        String icon = jsonParam.getString("icon");

        JSONObject result = new JSONObject();

        result.put("isSuccess", true);
        result.put("sellerId", sellerService.register(password, name, type, address, phone, startHour, endHour, icon));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getSellerInfo(String sellerId) {
        SellerEntity sellerEntity = sellerService.getSellerInfo(sellerId);

        JSONObject result = new JSONObject();

        result.put("name", sellerEntity.getName());
        result.put("type", sellerEntity.getType());
        result.put("address", sellerEntity.getAddress());
        result.put("password", sellerEntity.getPassword());
        result.put("phone", sellerEntity.getPhone());
        result.put("startHour", sellerEntity.getStartHour());
        result.put("endHour", sellerEntity.getEndHour());
        result.put("foodType", sellerEntity.getFoodType().split(Const.regix));
        result.put("discount", sellerEntity.getDiscount().split(Const.regix));
        result.put("icon", sellerEntity.getIcon());
        result.put("orderCount", sellerEntity.getOrderCount());

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String modifyCustomerInfo(@RequestBody JSONObject jsonParam) {
        String sellerId = jsonParam.getString("sellerId");

        String name = jsonParam.getString("name");
        String password = jsonParam.getString("password");
        String type = jsonParam.getString("type");
        String address = jsonParam.getString("address");
        String phone = jsonParam.getString("phone");
        String startHour = jsonParam.getString("startHour");
        String endHour = jsonParam.getString("endHour");
        String icon = jsonParam.getString("icon");

        String foodType = Const.convertJSONArrayToString(jsonParam.getJSONArray("foodType"));
        String discount = Const.convertJSONArrayToString(jsonParam.getJSONArray("discount"));

        JSONObject result = new JSONObject();

        result.put("isSuccess", sellerService.modifyInfo(sellerId, password, name, type, address, phone, startHour,
                endHour, foodType, discount, icon));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/food/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String addFood(@RequestBody JSONObject jsonParam) {
        String sellerId = jsonParam.getString("sellerId");

        String name = jsonParam.getString("name");
        String description = jsonParam.getString("description");
        String foodType = jsonParam.getString("foodType");
        String photo = jsonParam.getString("photo");
        Double money = jsonParam.getDouble("money");
        Double discountMoney = jsonParam.getDouble("discountMoney");
        Date startTime = jsonParam.getDate("startTime");
        Date endTime = jsonParam.getDate("endTime");
        Integer stock = jsonParam.getInteger("stock");

        JSONObject result = new JSONObject();

        result.put("isSuccess", sellerService.addFood(sellerId, name, photo, foodType, money, discountMoney,
                startTime, endTime, stock, description));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/food/modify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String modifyFood(@RequestBody JSONObject jsonParam) {
        Integer foodId = jsonParam.getInteger("foodId");
        String sellerId = jsonParam.getString("sellerId");

        String name = jsonParam.getString("name");
        String description = jsonParam.getString("description");
        String foodType = jsonParam.getString("foodType");
        String photo = jsonParam.getString("photo");
        Double money = jsonParam.getDouble("money");
        Double discountMoney = jsonParam.getDouble("discountMoney");
        Date startTime = jsonParam.getDate("startTime");
        Date endTime = jsonParam.getDate("endTime");
        Integer stock = jsonParam.getInteger("stock");

        JSONObject result = new JSONObject();

        result.put("isSuccess", sellerService.modifyFood(foodId, sellerId, name, photo, foodType, money, discountMoney,
                startTime, endTime, stock, description));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/food/delete", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public String deleteFood(Integer foodId) {
        JSONObject result = new JSONObject();

        List<Integer> list = new ArrayList<>();
        list.add(foodId);

        result.put("isSuccess", sellerService.deleteFood(list));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/food/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getAllFood(String sellerId) {
        JSONObject result = new JSONObject();

        result.put("foodList", sellerService.getFoodListBySeller(sellerId));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/type/modify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String modifyFoodType(@RequestBody JSONObject jsonParam) {
        String types = Const.convertJSONArrayToString(jsonParam.getJSONArray("foodType"));
        String sellerId = jsonParam.getString("sellerId");

        JSONObject result = new JSONObject();

        result.put("isSuccess", sellerService.modifyFoodType(sellerId, types));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/discount/customer/modify", method = RequestMethod.POST, produces = "application/json;" +
            "charset=UTF-8")
    public String modifyCustomerDiscount(@RequestBody JSONObject jsonParam) {
        String discount = Const.convertJSONArrayToString(jsonParam.getJSONArray("discount"));
        String sellerId = jsonParam.getString("sellerId");

        JSONObject result = new JSONObject();

        result.put("isSuccess", sellerService.modifyCustomerDiscount(sellerId, discount));

        return result.toJSONString();
    }

}
