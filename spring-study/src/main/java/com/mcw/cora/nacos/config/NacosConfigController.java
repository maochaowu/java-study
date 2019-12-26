package com.mcw.cora.nacos.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @Author yibi
 * Date 2019/7/4 21:53
 * Version 1.0
 **/
@Controller
@RequestMapping("config")
public class NacosConfigController {

    @NacosValue(value = "${message}", autoRefreshed = true)
    private String message;

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public String get() {
        return message;
    }
}
