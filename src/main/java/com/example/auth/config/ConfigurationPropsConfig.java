

package com.example.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.example.auth.service.type.JwtProperties;

@Configuration
@EnableConfigurationProperties(value = {JwtProperties.class})
public class ConfigurationPropsConfig {
}