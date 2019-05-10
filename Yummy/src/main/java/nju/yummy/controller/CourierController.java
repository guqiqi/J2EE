package nju.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import nju.yummy.entity.CourierEntity;
import nju.yummy.service.CourierService;
import nju.yummy.serviceImpl.CourierServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courier")
public class CourierController {
    private CourierService courierService;

    public CourierController() {
        courierService = new CourierServiceImpl();
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String login(@RequestBody JSONObject jsonParam) {
        String email = jsonParam.getString("phone");
        String password = jsonParam.getString("password");

        JSONObject result = new JSONObject();

        String mes = courierService.login(email, password);

        result.put("isValid", !mes.equals("该账号不存在") && !mes.equals("账号密码不匹配"));
        result.put("mes", mes);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/signUp", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String register(@RequestBody JSONObject jsonParam) {
        String password = jsonParam.getString("password");
        String username = jsonParam.getString("username");
        String phone = jsonParam.getString("phone");

        JSONObject result = new JSONObject();

        result.put("isSuccess", courierService.register(username, phone, password));

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getCourierInfo(Integer courierId) {
        CourierEntity courierEntity = courierService.getInfo(courierId);

        JSONObject result = new JSONObject();

        result.put("phone", courierEntity.getPhone());
        result.put("username", courierEntity.getUsername());
        result.put("password", courierEntity.getPassword());

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String modifyCustomerInfo(@RequestBody JSONObject jsonParam) {
        Integer courierId = jsonParam.getInteger("courierId");
        String username = jsonParam.getString("username");
        String phone = jsonParam.getString("phone");
        String password = jsonParam.getString("password");

        JSONObject result = new JSONObject();

        result.put("isSuccess", courierService.modifyInfo(courierId, username, phone, password));

        return result.toJSONString();
    }

}
