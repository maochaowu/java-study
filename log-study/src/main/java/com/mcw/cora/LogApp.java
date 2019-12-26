package com.mcw.cora;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class LogApp {

    private static final Logger log = LoggerFactory.getLogger(LogApp.class);

    public static void main(String[] args) {
        SpringApplication.run(LogApp.class, args);
    }

    @RestController
    class LogController {

        @Value("${server.port}")
        private Integer serverPort;

        @RequestMapping("/start")
        public String logStart() {
            log.info("应用启动log，serverPort={}", serverPort);
            return "log start";
        }


        @RequestMapping("/stop")
        public String logStop() {
            log.info("应用停掉log，serverPort={}", serverPort);
            return "log stop";
        }

        @RequestMapping("/print")
        public String printLog() {
            log.info("应用开始执行数据日志，serverPort={}", serverPort);

            for (int i = 0; i < 10; i++) {
                Thread tt = new Thread(() -> {
                    int j = 0;
                    while (j < 1000) {
                        j++;
                        log.info("输出日志，serverPort={}，线程={}，j={}", serverPort, Thread.currentThread().getName(), j);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    log.info("线程执行完成,serverPort={}，线程={}", serverPort, Thread.currentThread().getName());
                });
                tt.start();
            }
            return "print";
        }

    }

}
