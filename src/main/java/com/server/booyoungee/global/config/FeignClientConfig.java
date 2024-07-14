package com.server.booyoungee.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.server.booyoungee.BooyoungeeApplication;

@Configuration
@EnableFeignClients(basePackageClasses = BooyoungeeApplication.class)
public class FeignClientConfig {
}