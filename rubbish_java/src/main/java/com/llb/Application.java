package com.llb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 启动类
 * @Author llb
 * Date on 2020/4/14
 */
@SpringBootApplication
@MapperScan("com.llb.mapper")
public class Application extends SpringBootServletInitializer {

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }

        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return super.configure(builder);
        }

}
