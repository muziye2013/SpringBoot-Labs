package cn.iocoder.springcloud.labx08.gatewaydemo.controller;

import cn.hutool.json.JSONUtil;
import cn.iocoder.springcloud.labx08.gatewaydemo.domain.Blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("blog")
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 测试 @Value 注解的属性
     */
    @PostMapping("/blog01")
    public Map<String, Object> blog01(@RequestBody Blog blog,HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("params" , request.getParameter("token"));
        result.put("bodydata" , JSONUtil.toJsonStr(blog));
        return result;
    }
}


