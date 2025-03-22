package com.megaport.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "config.properties";
    
    static {
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new RuntimeException("Config file not found: " + CONFIG_FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading config file", e);
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
} 