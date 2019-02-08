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
                /*
                 * 这段代码执行完毕之后，图片上传到了工程的跟路径； 大家自己扩散下思维，如果我们想把图片上传到
                 * d:/files大家是否能实现呢？ 等等;
                 * 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名；
                 * 3、文件格式; 4、文件大小的限制;
                 */
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