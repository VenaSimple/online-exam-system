package com.exam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.exam.mapper")
public class ExamApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
        System.out.println("========================================");
        System.out.println("   在线培训考试系统启动成功！");
        System.out.println("   接口文档: http://localhost:8088/api/doc.html");
        System.out.println("========================================");
    }
}
