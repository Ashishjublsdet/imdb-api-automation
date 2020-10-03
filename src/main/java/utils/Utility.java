package utils;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;


public class Utility {
    private static Properties properties;

    public static Properties loadProperty(String path) throws Exception {
        if (properties == null) {
            properties = new Properties();
            properties.load(new FileInputStream(path));
            return properties;
        }
        return properties;
    }

    public static List<Map.Entry<String, Float>> getSortedMap(Map<String, Float> map) {
        return map.entrySet()
                .stream()
                .sorted(reverseOrder(Map.Entry.comparingByValue()))
                .limit(3).collect(Collectors.toList());
    }
}
