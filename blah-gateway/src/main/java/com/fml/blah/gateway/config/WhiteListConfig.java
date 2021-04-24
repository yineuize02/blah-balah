package com.fml.blah.gateway.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "blah.whitelist")
public class WhiteListConfig {
  private List<String> urls;
}
