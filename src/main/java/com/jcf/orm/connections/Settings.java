package com.jcf.orm.connections;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private final Properties properties = new Properties();
    private static final String PROPERTIES_PATH_1 = "src/main/resources/application.properties";

    private Settings() {
        try {
            properties.load(new FileInputStream(PROPERTIES_PATH_1));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class SettingsHolder {
        private static final Settings INSTANCE = new Settings();
    }

    public static Settings getInstance() {
        return SettingsHolder.INSTANCE;
    }

    public String getValue(String key) {
        return this.properties.getProperty(key);
    }
}
