package com.kevin.crawler.config;

import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitializeConfiguration {

  private static final Logger log = LoggerFactory.getLogger(InitializeConfiguration.class);

  private static final Properties properties = new Properties();

  public static Properties initializeConfig() {
    if (properties.isEmpty()) {
      ClassLoader classLoader = InitializeConfiguration.class.getClassLoader();
      InputStream inputStream = classLoader.getResourceAsStream("application.properties");
      if (inputStream == null) {
        return properties;
      }

      try {
        properties.load(inputStream);
        log.info(properties.toString());
      } catch (Exception e) {
        log.error("loading config error：{}", e.getMessage());
      }
    }
    return properties;
  }

}
