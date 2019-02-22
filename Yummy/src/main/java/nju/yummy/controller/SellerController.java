package nju.yummy.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import nju.yummy.entity.DiscountTableEntity;
import nju.yummy.entity.SellerEntity;
import nju.yummy.service.SellerService;
import nju.yummy.serviceImpl.SellerServiceImpl;
import nju.yummy.util.Const;
import nju.yummy.util.StatisticUtil;
import nju.yummy.vo.SellerCostVO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {
    private SellerService sellerService;

    public SellerController() {
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
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getAllSeller() {
        List<SellerEntity> sellerEntities = sellerService.getAllSellerEntities();

        JSONObject result = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for(SellerEntity sellerEntity: sellerEntities) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", sellerEntity.getName());
            jsonObject.put("type", sellerEntity.getType());
            jsonObject.put("address", sellerEntity.getAddress());
            jsonObject.put("password", sellerEntity.getPassword());
            jsonObject.put("phone", sellerEntity.getPhone());
            jsonObject.put("startHour", sellerEntity.getStartHour());
            jsonObject.put("endHour", sellerEntity.getEndHour());
            jsonObject.put("foodType", sellerEntity.getFoodType().split(Const.regix));
            jsonObject.put("discount", sellerEntity.getDiscount().split(Const.regix));
            jsonObject.put("icon", sellerEntity.getIcon());
            jsonObject.put("orderCount", sellerEntity.getOrderCount());
            jsonObject.put("status", sellerEntity.getStatus());
            jsonObject.put("sellerId", sellerEntity.getSellerId());

            jsonObject.put("tempIcon", sellerEntity.getTempIcon());
            jsonObject.put("tempName", sellerEntity.getTempName());
            jsonObject.put("tempAddress", sellerEntity.getTempAddress());
            jsonObject.put("tempPhone", sellerEntity.getTempPhone());
            jsonObject.put("tempPassword", sellerEntity.getTempPassword());
            jsonObject.put("tempStartHour", sellerEntity.getTempStartHour());
            jsonObject.put("tempEndHour", sellerEntity.getTempEndHour());
            jsonObject.put("tempType", sellerEntity.getTempType());

            jsonArray.add(jsonObject);
        }

        result.put("sellerList", jsonArray);

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
    public String modifySellerInfo(@RequestBody JSONObject jsonParam) {
        String sellerId = jsonParam.getString("sellerId");

        String name = jsonParam.getString("name");
        String password = jsonParam.getString("password");
        String type = jsonParam.getString("type");
        String address = jsonParam.getString("address");
        String phone = jsonParam.getString("phone");
        String startHour = jsonParam.getString("startHour");
        String endHour = jsonParam.getString("endHour");
        String icon = jsonParam.getString("icon");

        JSONObject result = new JSONObject();

        result.put("isSuccess", sellerService.modifyInfo(sellerId, password, name, type, address, phone, startHour,
                endHour, icon, 3));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/food/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String addFood(@RequestBody JSONObject jsonParam) {
        String sellerId = jsonParam.getString("sellerId");

        String name = jsonParam.getString("name");
        String description = jsonParam.getString("description");
        String foodType = jsonParam.getString("foodType");
        System.out.println(foodType);
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

    @ResponseBody
    @RequestMapping(value = "/discount/composition/all", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public String getCompositionDiscount(String sellerId) {
        JSONObject result = new JSONObject();

        List<DiscountTableEntity> discountTableEntities = sellerService.getDiscountBySeller(sellerId);

        JSONArray composedDiscounts = new JSONArray();
        for (int i = 0; i < discountTableEntities.size(); i++) {
            JSONObject jsonpObject = new JSONObject();
            DiscountTableEntity discountTableEntity = discountTableEntities.get(i);
            jsonpObject.put("discountId", discountTableEntity.getDiscountId());
            jsonpObject.put("discountMoney", discountTableEntity.getDiscountMoney());
            jsonpObject.put("money", discountTableEntity.getMoney());

            JSONArray date = new JSONArray();
            date.add(discountTableEntity.getStartTime());
            date.add(discountTableEntity.getEndTime());
            jsonpObject.put("date", date);

            jsonpObject.put("startTime", discountTableEntity.getStartTime());
            jsonpObject.put("endTime", discountTableEntity.getEndTime());
            jsonpObject.put("foodIds", discountTableEntity.getFoodIds().split(Const.regix));
            jsonpObject.put("foodNames", discountTableEntity.getFoodNames().split(Const.regix));

            composedDiscounts.add(jsonpObject);
        }

        result.put("composedDiscounts", composedDiscounts);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/discount/composition/add", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String addCompositionDiscount(@RequestBody JSONObject jsonParam) {
        double discountMoney = jsonParam.getDouble("discountMoney");
        double money = jsonParam.getDouble("money");
        String sellerId = jsonParam.getString("sellerId");
        String foodIds = Const.convertJSONArrayToString(jsonParam.getJSONArray("foodIds"));
        Date startTime = jsonParam.getDate("startTime");
        Date endTime = jsonParam.getDate("endTime");

        JSONArray foodNames = new JSONArray();
        for (int i = 0; i < jsonParam.getJSONArray("foodIds").size(); i++) {
            foodNames.add(sellerService.getFoodInfo(Integer.parseInt(jsonParam.getJSONArray("foodIds").get(i).toString())).getName());
        }

        JSONObject result = new JSONObject();

        result.put("isSuccess", sellerService.addGroupDiscount(sellerId, foodIds, Const.convertJSONArrayToString(foodNames),
                discountMoney, money,
                startTime, endTime));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/discount/composition/modify", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public String modifyCompositionDiscount(@RequestBody JSONObject jsonParam) {
        Integer discountId = jsonParam.getInteger("discountId");
        double discountMoney = jsonParam.getDouble("discountMoney");
        double money = jsonParam.getDouble("money");
        String sellerId = jsonParam.getString("sellerId");
        String foodIds = Const.convertJSONArrayToString(jsonParam.getJSONArray("foodIds"));

        JSONArray foodNames = new JSONArray();
        for (int i = 0; i < jsonParam.getJSONArray("foodIds").size(); i++) {
            foodNames.add(sellerService.getFoodInfo(Integer.parseInt(jsonParam.getJSONArray("foodIds").get(i).toString())).getName());
        }

        Date startTime = jsonParam.getDate("startTime");
        Date endTime = jsonParam.getDate("endTime");

        JSONObject result = new JSONObject();

        result.put("isSuccess", sellerService.modifyGroupDiscount(discountId, sellerId, foodIds,
                Const.convertJSONArrayToString(foodNames), discountMoney, money, startTime, endTime));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/discount/composition/delete", method = RequestMethod.DELETE,
            produces = "application/json;charset=UTF-8")
    public String deleteGroupDiscount(Integer discountId) {
        JSONObject result = new JSONObject();

        List<Integer> list = new ArrayList<>();
        list.add(discountId);

        result.put("isSuccess", sellerService.deleteGroupDiscount(list));

        return result.toJSONString();
    }


    @ResponseBody
    @RequestMapping(value = "/verify", method = RequestMethod.PATCH,
            produces = "application/json;charset=UTF-8")
    public String verifySeller(String sellerId, boolean isPass) {
        JSONObject result = new JSONObject();

        result.put("isSuccess", sellerService.verifySeller(sellerId, isPass));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/statistic/cost", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getSellStatistic(String sellerId) {
        double[] costByHour = sellerService.getCostByHour(sellerId);

        JSONObject result = new JSONObject();

        result.put("sellByHour", StatisticUtil.getCostBy2Hour(costByHour));

        List<SellerCostVO> sellerCostVOList = sellerService.getCostByCustomer(sellerId);
        result.put("sellTable", sellerCostVOList);

        result.put("recentVolume", sellerService.getCostByTime(sellerId));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/statistic/cancel", method = RequestMethod.GET, produces = "application/json;" +
            "charset=UTF-8")
    public String getCancelStatistic(String email) {
        JSONObject result = new JSONObject();
        // TODO

        return result.toJSONString();
    }
}
