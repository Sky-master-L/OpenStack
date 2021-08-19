package com.lrj.openstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OpenstackApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenstackApiApplication.class, args);
    }

}
