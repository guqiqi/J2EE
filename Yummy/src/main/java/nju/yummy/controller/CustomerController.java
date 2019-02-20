package nju.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import nju.yummy.entity.AddressEntity;
import nju.yummy.entity.CustomerEntity;
import nju.yummy.service.CustomerService;
import nju.yummy.serviceImpl.CustomerServiceImpl;
import nju.yummy.util.StatisticUtil;
import nju.yummy.util.TokenProcessor;
import nju.yummy.vo.SellerCostVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController() {
        customerService = new CustomerServiceImpl();
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String login(@RequestBody JSONObject jsonParam) {
        String email = jsonParam.getString("email");
        String password = jsonParam.getString("password");

        JSONObject result = new JSONObject();

        String mes = customerService.login(email, password);

        result.put("isValid", mes.equals("成功登陆"));
        result.put("mes", mes);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String addCustomerInfo(@RequestBody JSONObject jsonParam) {
        String email = TokenProcessor.getId(jsonParam.getString("email"));
        String password = jsonParam.getString("password");
        String username = jsonParam.getString("username");
        String phone = jsonParam.getString("phone");

        JSONObject result = new JSONObject();

        result.put("isSuccess", customerService.addCustomerInfo(email, username, password, "", phone));
        result.put("email", email);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getCustomerInfo(String email) {
        CustomerEntity customerEntity = customerService.getCustomerInfo(email);

        JSONObject result = new JSONObject();

        result.put("phone", customerEntity.getPhone());
        result.put("point", customerEntity.getPoint());
        result.put("username", customerEntity.getUsername());
        result.put("password", customerEntity.getPassword());

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String modifyCustomerInfo(@RequestBody JSONObject jsonParam) {
        String email = jsonParam.getString("email");
        String username = jsonParam.getString("username");
        String phone = jsonParam.getString("phone");
        String password = jsonParam.getString("password");

        JSONObject result = new JSONObject();

        result.put("isSuccess", customerService.modifyCustomerInfo(email, username, password, "", phone));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/writeOff", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String writeOff(String email) {
        boolean res = customerService.writeOff(email);

        JSONObject result = new JSONObject();

        result.put("isSuccess", res);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/address/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getAddress(String email) {
        List<AddressEntity> addressEntityList = customerService.getAddressByEmail(email);

        JSONObject result = new JSONObject();

        result.put("addressList", addressEntityList);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/address/modify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String modifyAddress(@RequestBody JSONObject jsonParam) {
        String email = jsonParam.getString("email");
        String receiver = jsonParam.getString("receiver");
        String phone = jsonParam.getString("phone");
        String detail = jsonParam.getString("detail");
        Integer addressId = jsonParam.getInteger("addressId");

        int label = (int)(Math.random() * 5);

        JSONObject result = new JSONObject();

        result.put("isSuccess", customerService.modifyAddress(addressId, email, receiver, detail, phone, label));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/address/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String addAddress(@RequestBody JSONObject jsonParam) {
        String email = jsonParam.getString("email");
        String receiver = jsonParam.getString("receiver");
        String phone = jsonParam.getString("phone");
        String detail = jsonParam.getString("detail");

        int label = (int)(Math.random() * 5);

        JSONObject result = new JSONObject();

        result.put("isSuccess", customerService.addAddress(email, receiver, detail, phone, label));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/address/delete", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public String deleteAddress(int addressId) {
        boolean res = customerService.deleteAddress(addressId);

        JSONObject result = new JSONObject();

        result.put("isSuccess", res);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/statistic/cost", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getCostStatistic(String email) {
        double[] costByHour = customerService.getCostByHour(email);

        JSONObject result = new JSONObject();

        result.put("costByHour", StatisticUtil.getCostBy2Hour(costByHour));

        List<SellerCostVO> sellerCostVOList = customerService.getCostClassifiedByType(email);
        result.put("costByType", StatisticUtil.getMoneyByType(sellerCostVOList));
        result.put("timesByType", StatisticUtil.getTimesByType(sellerCostVOList));

        result.put("costTable", customerService.getCostBySeller(email));

        result.put("recentVolume", customerService.getCostByTime(email));

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
