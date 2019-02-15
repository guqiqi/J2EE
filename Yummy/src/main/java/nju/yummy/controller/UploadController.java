package nju.yummy.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {
    @ResponseBody
    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST, produces = "multipart/form-data;charset=UTF-8")
    public String imgUpload(@RequestParam("file") MultipartFile file) {
        JSONObject result = new JSONObject();
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("../yummy-frontend/static/uploadImages/" + file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                result.put("message", e.getMessage());
                return result.toJSONString();
            } catch (IOException e) {
                result.put("message", e.getMessage());
                return result.toJSONString();
            } finally {
                result.put("url", "../../static/uploadImages/" + file.getOriginalFilename());
                return result.toJSONString();
            }
        } else {
            result.put("message", "文件为空");
            return result.toJSONString();
        }
    }
}