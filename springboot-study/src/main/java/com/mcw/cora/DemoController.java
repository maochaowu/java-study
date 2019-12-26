package com.mcw.cora;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yibi
 * Date 2019/7/11 21:24
 * Version 1.0
 **/
@RestController
@RequestMapping("/boot")
public class DemoController {

    @NacosValue(value = "${message}",autoRefreshed=true)
    private String message;

    @RequestMapping("test")
    public String test() {
        return message;
    }
}
