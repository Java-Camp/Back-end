package com.jcf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jcf")
public class EntryPoint{

    public static void main(String[] args) {
        SpringApplication.run(EntryPoint.class, args);
    }
}
