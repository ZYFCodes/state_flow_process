package org.zyf.stateflowprocess;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yanfengzhang
 * @description 状态流转处理启动类
 * @date 2021/4/25  22:50
 */
@SpringBootApplication
@MapperScan(basePackages = "org.zyf.stateflowprocess.mapper")
public class StateFlowProcessStartApp {
    public static void main(String[] args) {
        SpringApplication.run(StateFlowProcessStartApp.class, args);
    }
}
