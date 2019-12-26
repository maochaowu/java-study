package com.mcw.web.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *@author mcw
 *@date 2019/12/25
 */
@Controller
public class DemoController {

    @RequestMapping("/")
    public String index () {
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "demo";
    }
}
