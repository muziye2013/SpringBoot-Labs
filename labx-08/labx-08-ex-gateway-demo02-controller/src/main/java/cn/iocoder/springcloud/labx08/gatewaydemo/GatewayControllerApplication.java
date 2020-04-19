package cn.iocoder.springcloud.labx08.gatewaydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  验证spring cloud gateway多次获取requestbody的使用方法
 * @author xujinggen
 * @date 2020-4-4
 * */
@SpringBootApplication
public class GatewayControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayControllerApplication.class, args);
    }

}
