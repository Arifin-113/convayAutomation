package Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties config;

    // Load the properties file when needed
    public static void loadConfig() {
        config = new Properties();
        try {
            // Use direct file path for simplicity
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            config.load(file);
            file.close();
        } catch (IOException e) {
            System.out.println("Error: Could not read config.properties. " + e.getMessage());
            throw new RuntimeException("Failed to load config file");
        }
    }

    // Get a property value by key
    public static String getProperty(String key) {
        // Load config if not already loaded
        if (config == null) {
            loadConfig();
        }
        String value = config.getProperty(key);
        if (value == null) {
            System.out.println("Error: Key '" + key + "' not found in config.properties");
            throw new RuntimeException("Key not found: " + key);
        }
        return value;
    }
}